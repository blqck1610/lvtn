package com.lvtn.utils.dto.order;

import com.lvtn.utils.common.PaymentMethod;
import com.lvtn.utils.dto.payment.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * OrderResponse
 * Version 1.0
 * Date: 21/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/11/2024        NGUYEN             create
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {
    private UUID id;
    private PaymentResponse paymentResponse;
}
