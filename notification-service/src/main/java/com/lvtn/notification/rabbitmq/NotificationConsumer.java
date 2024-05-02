package com.lvtn.notification.rabbitmq;


import com.lvtn.clients.notification.NotificationRequest;
import com.lvtn.notification.service.NotificationService;
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
    public void consumerNotification(NotificationRequest notificationRequest){
        log.info("consumer  {} from queue" , notificationRequest);
        service.send(notificationRequest);
    }

}
