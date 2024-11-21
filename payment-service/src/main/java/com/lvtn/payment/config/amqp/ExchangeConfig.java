package com.lvtn.payment.config.amqp;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ExchangeConfig
 * Version 1.0
 * Date: 18/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/11/2024        NGUYEN             create
 */
@Configuration
public class ExchangeConfig {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(internalExchange);
    }
}
