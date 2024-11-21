package com.lvtn.payment.config.amqp;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@RequiredArgsConstructor
public class InventoryConfig {
    @Value("${rabbitmq.queue.inventory}")
    private String inventoryQueue;
    @Value("${rabbitmq.routing-keys.internal-inventory}")
    private String internalInventoryRoutingKey;

    private final TopicExchange topicExchange;

    @Bean
    public Queue inventoryQueue() {
        return new Queue(inventoryQueue);
    }

    @Bean
    public Binding internalInventoryBinding() {
        return BindingBuilder
                .bind(inventoryQueue())
                .to(topicExchange)
                .with(internalInventoryRoutingKey);
    }
}
