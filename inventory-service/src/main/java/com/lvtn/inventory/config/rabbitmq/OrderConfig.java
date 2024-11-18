package com.lvtn.inventory.config.rabbitmq;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OrderConfig
 * Version 1.0
 * Date: 18/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/11/2024        NGUYEN             create
 */
@Configuration
@Data
public class OrderConfig {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.queue.order}")
    private String orderQueue;
    @Value("${rabbitmq.routing-keys.internal-order}")
    private String internalOrderRoutingKey;

    private final TopicExchange topicExchange;

    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }

    @Bean
    public Binding internalOrderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(topicExchange)
                .with(internalOrderRoutingKey);
    }
}
