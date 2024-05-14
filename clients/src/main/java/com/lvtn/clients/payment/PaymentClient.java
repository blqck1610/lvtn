package com.lvtn.clients.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.UnsupportedEncodingException;

@FeignClient(value = "PAYMENT-SERVICE", path = "/api/v1/payment")
public interface PaymentClient {

    @GetMapping(value = "/vnpay/create-payment")
    public ResponseEntity<?> createVNPayPayment(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException;

    @GetMapping("/vnpay/transaction-info")
    public ResponseEntity<?> transactionVNPay();

    @GetMapping(value = "test")
    public ResponseEntity<String> test();




}
