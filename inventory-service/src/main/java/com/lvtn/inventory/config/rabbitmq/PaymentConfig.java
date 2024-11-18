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
 * PaymentConfig
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
public class PaymentConfig {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.queue.payment}")
    private String paymentQueue;
    @Value("${rabbitmq.routing-keys.internal-payment}")
    private String internalPaymentRoutingKey;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentQueue);
    }

    @Bean
    public Binding internalPaymentBinding() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(topicExchange())
                .with(internalPaymentRoutingKey);
    }
}
