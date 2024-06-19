package com.lvtn.order.service;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.clients.notification.OrderConfirmInformation;
import com.lvtn.clients.product.PurchaseRequest;
import com.lvtn.clients.user.UserClient;
import com.lvtn.clients.user.UserDto;
import com.lvtn.order.dto.OrderLineRequest;
import com.lvtn.order.dto.OrderRequest;
import com.lvtn.order.entity.Order;
import com.lvtn.order.entity.OrderLine;
import com.lvtn.order.rabbitmq.OrderAMQPConfig;
import com.lvtn.order.repository.OrderRepository;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserClient userClient;
    private final OrderRepository orderRepository;
    private final Mapper mapper;
    private final OrderLineService orderLineService;

    private final OrderAMQPConfig amqpConfig;
    private final RabbitMQMessageProducer producer;

    public Integer createOrder(OrderRequest orderRequest){
//        todo: check the user --> openfeign
        UserDto userDto = userClient.getUserInfo(orderRequest.getCustomerId()).getBody();
        if(userDto == null){
            throw new BaseException(200, "cannot create order :: No customer exists for with the provided id: " + orderRequest.getCustomerId());
        }


        Order order = this.orderRepository.save(mapper.toOrder(orderRequest));

        for(PurchaseRequest purchaseRequest : orderRequest.getProducts()) {
            orderLineService.saveOrderLine(new OrderLineRequest(
                    null, order.getId(), purchaseRequest.getProductId(), purchaseRequest.getQuantity()
            ));
        }
//        todo: start payment process
//        todo: send order comfirm information --> notifications
        return null;



    }

    public Integer testProducer() {
        OrderConfirmInformation orderConfirmInformation = OrderConfirmInformation.builder()
                .a("asd")
                .build();
        producer.publish(orderConfirmInformation, amqpConfig.getInternalExchange(), amqpConfig.getInternalOrderRoutingKey());
        return 1;
    }
}
