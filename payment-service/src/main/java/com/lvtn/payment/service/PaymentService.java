package com.lvtn.payment.service;


import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.payment.entity.Transaction;
import com.lvtn.payment.repository.PaymentRepository;
import com.lvtn.utils.dto.order.CreateOrderRequest;
import com.lvtn.utils.dto.payment.PaymentStatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RabbitMQMessageProducer producer;


    @Transactional
    public void saveTransaction(Transaction transaction){
        PaymentStatusResponse paymentStatusResponse = PaymentStatusResponse.builder()
                .orderId(transaction.getOrderId())
                .responseCode(transaction.getResponseCode())
                .paymentStatus(transaction.getTransactionStatus())
                .build();
        paymentRepository.save(transaction);

//        publishToOrder(paymentStatusResponse);
    }

    public void publishToOrder(PaymentStatusResponse paymentStatusResponse){
        log.info("publish payment status to order: " + paymentStatusResponse);
//        producer.publish(paymentStatusResponse, rabbitMQConfig.getInternalExchange(), rabbitMQConfig.getInternalPaymentRoutingKey());
    }

    public void createPayment(CreateOrderRequest request) {
    }
}
