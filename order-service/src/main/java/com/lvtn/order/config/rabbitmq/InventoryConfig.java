package com.lvtn.order.config.rabbitmq;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * InventoryConfig
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
public class InventoryConfig {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.queue.inventory}")
    private String inventoryQueue;
    @Value("${rabbitmq.routing-keys.internal-inventory}")
    private String internalInventoryRoutingKey;

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue queue(){
        return new Queue(inventoryQueue);
    }

    @Bean
    public Binding internalNotificationBinding(){
        return BindingBuilder
                .bind(queue())
                .to(topicExchange())
                .with(internalInventoryRoutingKey);
    }
}
