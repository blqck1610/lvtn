package com.lvtn.order.rabbitmq;

import com.lvtn.order.service.OrderService;
import com.lvtn.utils.dto.notification.NotificationRequest;
import com.lvtn.utils.dto.order.CancelOrderRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQConsumer
 * Version 1.0
 * Date: 18/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/11/2024        NGUYEN             create
 */
@Component
@Slf4j
@AllArgsConstructor
public class OrderConsumer {
    private final OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.queue.order}")
    @Observed
    public void consumerNotification(CancelOrderRequest request) {
        orderService.cancelOrder(request);
    }
}
