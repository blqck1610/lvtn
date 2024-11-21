package com.lvtn.notification.rabbitmq;


import com.lvtn.notification.service.NotificationService;
import com.lvtn.utils.dto.notification.NotificationRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {
    private final NotificationService service;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    @Observed
    public void consumerNotification(NotificationRequest notificationRequest) {
        service.send(notificationRequest);
    }
}
