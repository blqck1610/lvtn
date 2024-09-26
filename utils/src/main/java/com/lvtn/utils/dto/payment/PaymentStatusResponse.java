package com.lvtn.utils.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  PaymentStatusResponse{
    private String orderId;
    private String paymentStatus;
    private String responseCode;
}
