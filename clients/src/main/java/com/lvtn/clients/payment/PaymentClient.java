package com.lvtn.clients.payment;

import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.order.CreateOrderRequest;
import com.lvtn.utils.dto.payment.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "PAYMENT-SERVICE", path = "/api/v1/payment")
public interface PaymentClient {

    @PostMapping
    ApiResponse<PaymentResponse> createPayment(CreateOrderRequest request);
}
