package com.lvtn.payment.RabbitMQ;

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
public class AMQPConfig {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.queue.payment}")
    private String paymentQueue;
    @Value("${rabbitmq.routing-keys.internal-payment}")
    private String internalPaymentRoutingKey;

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue queue(){
        return new Queue(paymentQueue);
    }

    @Bean
    public Binding internalNotificationBinding(){
        return BindingBuilder
                .bind(queue())
                .to(topicExchange())
                .with(internalPaymentRoutingKey);
    }
}
