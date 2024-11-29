package com.lvtn.order.service.imp;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.clients.inventory.InventoryClient;
import com.lvtn.clients.payment.PaymentClient;
import com.lvtn.order.config.rabbitmq.InventoryConfig;
import com.lvtn.order.entity.Order;
import com.lvtn.order.entity.OrderLine;
import com.lvtn.order.repository.IOrderLineRepository;
import com.lvtn.order.repository.IOrderRepository;
import com.lvtn.order.service.OrderService;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.common.OrderStatus;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.order.CancelOrderRequest;
import com.lvtn.utils.dto.order.CreateOrderRequest;
import com.lvtn.utils.dto.order.CreateOrderResponse;
import com.lvtn.utils.dto.order.ItemDto;
import com.lvtn.utils.dto.payment.PaymentResponse;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * OrderServiceImp
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
    private final IOrderRepository iOrderRepository;
    private final IOrderLineRepository iOrderLineRepository;
    private final InventoryConfig inventoryConfig;
    private final PaymentClient paymentClient;
    private final InventoryClient inventoryClient;
    private final RabbitMQMessageProducer producer;

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        String username = getUsername();
        Order order = Order.builder()
                .status(OrderStatus.ORDER_PENDING)
                .username(username)
                .totalAmount(getTotalAmount(request))
                .build();
        ApiResponse<Object> inventoryResponse = inventoryClient.updateInventory(request.getItems());
        if (inventoryResponse.getCode() != HttpStatus.OK.value()) {
            throw new BaseException(inventoryResponse.getCode(), inventoryResponse.getMessage());
        }
        order = iOrderRepository.saveAndFlush(order);
        save(request.getItems(), order);
        request.setId(order.getId());
        ApiResponse<PaymentResponse> paymentResponse = paymentClient.createPayment(request);
        if (paymentResponse.getCode() != HttpStatus.OK.value()) {
            order.setStatus(OrderStatus.ORDER_CANCELLED);
            order.setNote(ErrorCode.CREATED_PAYMENT_FAILED.getMessage());
            iOrderRepository.save(order);
            producer.publish(request, inventoryConfig.getInternalExchange(), inventoryConfig.getInternalInventoryRoutingKey());
            throw new BaseException(ErrorCode.CREATED_PAYMENT_FAILED.getCode(), ErrorCode.CREATED_PAYMENT_FAILED.getMessage());
        }
        return new CreateOrderResponse(order.getId(), paymentResponse.getData());
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new BaseException(ErrorCode.UNAUTHENTICATED.getCode(), ErrorCode.UNAUTHENTICATED.getMessage());
        }
        return authentication.getName();
    }

    private Double getTotalAmount(CreateOrderRequest request) {
        double totalAmount = 0;
        for (ItemDto item : request.getItems()) {
            totalAmount += item.getPrice() + item.getQuantity();
        }
        return totalAmount;
    }

    @Transactional
    public void save(List<ItemDto> items, Order order) {
        for (ItemDto item : items) {
            OrderLine orderLine = OrderLine.builder()
                    .order(order)
                    .productId(item.getProductId())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .build();
            iOrderLineRepository.save(orderLine);
        }
    }

    @Override
    @Transactional
    public void cancelOrder(CancelOrderRequest request) {
        Order order = iOrderRepository.getReferenceById(request.getOrderId());
        order.setStatus(OrderStatus.ORDER_CANCELLED);
        order.setNote(request.getReason());
        iOrderRepository.save(order);
    }
}
