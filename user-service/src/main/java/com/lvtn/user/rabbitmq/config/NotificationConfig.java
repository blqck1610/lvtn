package com.lvtn.user.rabbitmq.config;

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

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue queue(){
        return new Queue(notificationQueue);
    }

    @Bean
    public Binding internalNotificationBinding(){
        return BindingBuilder
                .bind(queue())
                .to(topicExchange())
                .with(internalNotificationRoutingKey);
    }

}
