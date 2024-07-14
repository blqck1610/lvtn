package com.lvtn.order.rabbitmq;


import com.lvtn.clients.payment.PaymentStatusResponse;
import com.lvtn.order.service.OrderService;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@AllArgsConstructor
public class OrderStatusConsumer {
    private final OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.queue.payment}")
    @Observed
    public void consumerOrderStatus(PaymentStatusResponse paymentStatusResponse) {
        log.info("consumer  {} from queue", paymentStatusResponse);
        orderService.saveOrderStatus(paymentStatusResponse);
    }
}
