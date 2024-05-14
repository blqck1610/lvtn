package com.lvtn.payment.service;


import com.lvtn.payment.entity.Transaction;
import com.lvtn.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional
    public void saveTransaction(Transaction transaction){
        paymentRepository.save(transaction);
    }

}
