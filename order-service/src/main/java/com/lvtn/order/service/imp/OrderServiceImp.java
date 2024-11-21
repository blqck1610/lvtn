package com.lvtn.order.service.imp;

import com.lvtn.clients.inventory.InventoryClient;
import com.lvtn.clients.payment.PaymentClient;
import com.lvtn.order.entity.Order;
import com.lvtn.order.entity.OrderLine;
import com.lvtn.order.repository.IOrderLineRepository;
import com.lvtn.order.repository.IOrderRepository;
import com.lvtn.order.service.OrderService;
import com.lvtn.utils.common.OrderStatus;
import com.lvtn.utils.dto.order.CancelOrderRequest;
import com.lvtn.utils.dto.order.ItemDto;
import com.lvtn.utils.dto.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PaymentClient paymentClient;
    private final InventoryClient inventoryClient;

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto request) {
        Order order = Order.builder()
                .status(OrderStatus.ORDER_PENDING)
                .userId(request.getUserId())
                .totalAmount(request.getTotalAmount())
                .build();
        order = iOrderRepository.saveAndFlush(order);
        save(request.getItems(), order);
        request.setId(order.getId());

        return request;
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
