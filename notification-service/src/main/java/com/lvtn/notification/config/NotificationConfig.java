package com.lvtn.notification.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class NotificationConfig {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;
    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKey;
    @Value("${rabbitmq.queue.order}")
    private String orderQueue;
    @Value("${rabbitmq.routing-keys.internal-order}")
    private String internalOrderRoutingKey;

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue notificationQueue(){
        return new Queue(notificationQueue);
    }
    @Bean
    public Queue orderQueue(){
        return new Queue(orderQueue);
    }

    @Bean
    public Binding internalNotificationBinding(){
        return BindingBuilder
                .bind(notificationQueue())
                .to(topicExchange())
                .with(internalNotificationRoutingKey);
    }
    @Bean
    public Binding internalOrderBinding(){
        return BindingBuilder
                .bind(orderQueue())
                .to(topicExchange())
                .with(internalOrderRoutingKey);
    }

}
