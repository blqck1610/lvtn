package com.lvtn.clients.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@FeignClient(value = "PAYMENT-SERVICE", path = "/api/v1/payment")
public interface PaymentClient {

    @PostMapping(value = "/create-payment/vnpay")
    public ResponseEntity<PaymentResponseDTO> createVNPayPayment(PaymentRequest paymentRequest) throws UnsupportedEncodingException;






}
