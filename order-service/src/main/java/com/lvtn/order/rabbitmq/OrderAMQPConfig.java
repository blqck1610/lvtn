package com.lvtn.order.rabbitmq;

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
public class OrderAMQPConfig {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.queue.order}")
    private String orderQueue;
    @Value("${rabbitmq.routing-keys.internal-order}")
    private String internalOrderRoutingKey;

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue queue(){
        return new Queue(orderQueue);
    }

    @Bean
    public Binding internalNotificationBinding(){
        return BindingBuilder
                .bind(queue())
                .to(topicExchange())
                .with(internalOrderRoutingKey);
    }

}
