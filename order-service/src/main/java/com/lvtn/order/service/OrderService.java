package com.lvtn.order.service;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.clients.notification.NotificationRequest;
import com.lvtn.clients.notification.NotificationType;
import com.lvtn.clients.notification.OrderConfirmation;
import com.lvtn.clients.payment.PaymentClient;
import com.lvtn.clients.payment.PaymentRequest;
import com.lvtn.clients.payment.PaymentResponseDTO;
import com.lvtn.clients.payment.TransactionDTO;
import com.lvtn.clients.product.ProductClient;
import com.lvtn.clients.product.PurchaseRequest;
import com.lvtn.clients.product.PurchaseResponse;
import com.lvtn.clients.user.UserClient;
import com.lvtn.clients.user.UserDto;
import com.lvtn.order.dto.OrderLineRequest;
import com.lvtn.order.dto.OrderRequest;
import com.lvtn.order.dto.OrderResponse;
import com.lvtn.order.entity.Order;
import com.lvtn.order.rabbitmq.OrderAMQPConfig;
import com.lvtn.order.repository.OrderRepository;
import com.lvtn.utils.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserClient userClient;
    private final OrderRepository orderRepository;
    private final Mapper mapper;
    private final OrderLineService orderLineService;
    private final ProductClient productClient;

    private final OrderAMQPConfig amqpConfig;
    private final RabbitMQMessageProducer producer;

    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest orderRequest){
//        check the user --> openfeign
        UserDto userDto = userClient.getUserInfo(orderRequest.getCustomerId()).getBody();
        if(userDto == null){
            throw new BaseException(200, "cannot create order :: No customer exists for with the provided id: " + orderRequest.getCustomerId());
        }

        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(orderRequest.getProducts()).getBody();

        Order order = this.orderRepository.saveAndFlush(mapper.toOrder(orderRequest));

        for(PurchaseRequest purchaseRequest : orderRequest.getProducts()) {
            orderLineService.saveOrderLine(new OrderLineRequest(
                    null, order.getId(), purchaseRequest.getProductId(), purchaseRequest.getQuantity()
            ));
        }
//        todo: start payment process


        OrderConfirmation orderConfirmation = OrderConfirmation.builder()
                .orderReference(order.getReference())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod().toString())
                .products(purchasedProducts)
                .build();
        String message = orderConfirmation.toString();
        NotificationRequest rq = NotificationRequest.builder()
                .type(NotificationType.ORDER_INFORMATION_CONFIRM)
                .customerEmail(userDto.getEmail())
                .message(message)
                .customerId(userDto.getId())
                .build();
        producer.publish(rq, amqpConfig.getInternalExchange(), amqpConfig.getInternalOrderRoutingKey());

        return order.getId();



    }




    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(mapper::toOrderResponse).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).map(mapper::toOrderResponse).orElseThrow(() -> new BaseException(404, "Order not found with id " + orderId));
    }

    public OrderResponse test(HttpServletRequest request) throws UnsupportedEncodingException {
        PaymentRequest paymentRequest = new PaymentRequest(10000, 1+"", getIpAddress(request));
        PaymentResponseDTO paymentResponseDTO = paymentClient.createVNPayPayment(paymentRequest).getBody();
        

        return null;

    }
    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }
}
