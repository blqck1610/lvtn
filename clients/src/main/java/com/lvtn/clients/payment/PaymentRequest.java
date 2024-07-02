package com.lvtn.clients.payment;

import lombok.Builder;

@Builder
public record PaymentRequest(

        long amount,
        String orderId


) {
}
