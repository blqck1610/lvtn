package com.lvtn.notification.rabbitmq;

import com.lvtn.clients.notification.NotificationRequest;
import com.lvtn.clients.notification.OrderConfirmInformation;
import com.lvtn.notification.service.NotificationService;
import com.lvtn.notification.service.OrderConfirmService;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderConsumer {

    private final OrderConfirmService service;

    @RabbitListener(queues = "${rabbitmq.queue.order}")
    @Observed
    public void consumerNotification(OrderConfirmInformation orderConfirmInformation){
        log.info("consumer  {} from queue", orderConfirmInformation);
        service.send(orderConfirmInformation);
    }



}
