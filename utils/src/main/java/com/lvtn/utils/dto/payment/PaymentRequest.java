package com.lvtn.utils.dto.payment;

import lombok.Builder;

@Builder
public record PaymentRequest(
        long amount,
        String orderId
) {
}
