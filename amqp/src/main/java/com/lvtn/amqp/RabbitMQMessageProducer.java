package com.lvtn.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class RabbitMQMessageProducer {
    private final AmqpTemplate amqpTemplate;
    public void publish(Object payload, String exchange, String routingKey) {
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Published to {} using routing-key {}. Payload: {}", exchange, routingKey, payload);
    }

}
