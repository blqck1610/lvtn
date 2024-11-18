package com.lvtn.order.config.rabbitmq;

import lombok.Data;
import org.springframework.amqp.core.Queue;
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
    @Value("${rabbitmq.queue.order}")
    private String orderQueue;

    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }

}
