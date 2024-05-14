package com.lvtn.payment.repository;

import com.lvtn.payment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Transaction, Integer> {
}
