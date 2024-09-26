package com.lvtn.order.service;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.clients.payment.*;
import com.lvtn.clients.product.ProductClient;
import com.lvtn.utils.dto.product.PurchaseResponse;
import com.lvtn.clients.user.UserClient;
import com.lvtn.utils.dto.order.OrderRequest;
import com.lvtn.utils.dto.order.OrderResponse;
import com.lvtn.order.entity.*;
import com.lvtn.order.rabbitmq.OrderAMQPConfig;
import com.lvtn.order.repository.OrderLineRepository;
import com.lvtn.order.repository.OrderRepository;
import com.lvtn.utils.dto.payment.PaymentRequest;
import com.lvtn.utils.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
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
    private final OrderLineRepository orderLineRepository;

    private final OrderAMQPConfig amqpConfig;
    private final RabbitMQMessageProducer producer;

    private final PaymentClient paymentClient;

    public Object createOrder(OrderRequest orderRequest, String username) throws UnsupportedEncodingException {
//        check the user --> openfeign
        UserDto userDto = userClient.findByUsername(username);
        if(userDto == null){
            throw new BaseException(200, "cannot create order :: No customer exists for with the provided id: " + username);
        }

        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(orderRequest.getProducts()).getBody();
        PaymentMethod paymentMethod = PaymentMethod.valueOf(orderRequest.getPaymentMethod().toUpperCase());

        assert purchasedProducts != null;

        List<OrderLine> orderLines = purchasedProducts.stream().map(mapper::toOrderLine).collect(Collectors.toList());


        Order order = Order.builder()
                .totalAmount(getTotalAmount(purchasedProducts))
                .customerId(userDto.getId())
                .paymentMethod(paymentMethod)
                .status(OrderStatus.ORDER_PLACE)
                .orderLines(orderLines)
                .build();
        order = orderRepository.saveAndFlush(order);
        for (OrderLine line : orderLines) {
            line.setOrder(order);
        }
        orderLineRepository.saveAllAndFlush(orderLines);
        productClient.clearCart(userDto.getUsername());

        if(!orderRequest.getPaymentMethod().equalsIgnoreCase("VNPAY")){
            order.setPaymentMethod(PaymentMethod.valueOf(orderRequest.getPaymentMethod().toUpperCase()));
            order.setPaymentStatus(PaymentStatus.PENDING);
            orderRepository.saveAndFlush(order);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("http://localhost:5173/confirm"));
            return headers;
        }



//        start payment process
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId().toString())
                .amount(order.getTotalAmount())

                .build();
        PaymentResponseDTO payment = paymentClient.createVNPayPayment(paymentRequest).getBody();


// no need
//        OrderConfirmation orderConfirmation = OrderConfirmation.builder()
//                .orderReference(order.getReference())
//                .totalAmount(order.getTotalAmount())
//                .paymentMethod(order.getPaymentMethod().toString())
//                .products(purchasedProducts)
//                .build();
//        String message = orderConfirmation.toString();
//        NotificationRequest rq = NotificationRequest.builder()
//                .type(NotificationType.ORDER_INFORMATION_CONFIRM)
//                .customerEmail(userDto.getEmail())
//                .message(message)
//                .customerId(userDto.getId())
//                .build();
//        producer.publish(rq, amqpConfig.getInternalExchange(), amqpConfig.getInternalOrderRoutingKey());

        return new ResponseEntity<>(payment, HttpStatus.FOUND);



    }




    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(mapper::toOrderResponse).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).map(mapper::toOrderResponse).orElseThrow(() -> new BaseException(404, "Order not found with id " + orderId));
    }

    public String test(HttpServletRequest request) throws UnsupportedEncodingException {
        PaymentRequest paymentRequest = new PaymentRequest(10000, 1+"");
        PaymentResponseDTO paymentResponseDTO = paymentClient.createVNPayPayment(paymentRequest).getBody();
        RestTemplate restTemplate = new RestTemplate();

        return paymentResponseDTO.getURL();

    }

    public void saveOrderStatus(PaymentStatusResponse paymentStatusResponse) {
        Order order = orderRepository.findById(Integer.parseInt(paymentStatusResponse.getOrderId())).orElse(null);
        PaymentStatus paymentStatus = PaymentStatus.PENDING;
        OrderStatus orderStatus = OrderStatus.ORDER_PLACE;
        if(paymentStatusResponse.getPaymentStatus().equals("00")){
            paymentStatus = PaymentStatus.PAID;
            orderStatus = OrderStatus.PROCESSING;

        } else if(paymentStatusResponse.getPaymentStatus().equals("01")){
            paymentStatus = PaymentStatus.IN_PROCESSING;
            orderStatus = OrderStatus.PROCESSING;
        }
        else {
            paymentStatus = PaymentStatus.ERROR;
        }

        if(order != null){
            order.setPaymentStatus(paymentStatus);

            orderRepository.save(order);
        }

//        return null;
    }

    private long getTotalAmount(List<PurchaseResponse> products){
        long totalAmount = 0;
        for(PurchaseResponse p : products){
            totalAmount += (long) (p.getQuantity() * p.getPrice());
        }
        return totalAmount * 21000;

    }

    public List<OrderResponse> getAllOrderForUser(String username) {
        UserDto userDto = userClient.findByUsername(username);
        assert userDto != null;
        List<OrderResponse> orderResponses = orderRepository.findAllByCustomerId(userDto.getId()).stream()
                .map(mapper::toOrderResponse).collect(Collectors.toList());
        return orderResponses;
    }

    public OrderResponse getOrder(Integer id) {
        return mapper.toOrderResponse(orderRepository.findById(id).orElseThrow(() -> new BaseException(404, "Order not found for id " + id)));
    }
}

