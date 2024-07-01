package com.lvtn.clients.payment;

public record PaymentRequest(

        long amount,
        String orderId,
        String ipAddress

) {
}
