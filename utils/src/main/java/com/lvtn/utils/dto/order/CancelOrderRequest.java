package com.lvtn.utils.dto.order;

import com.lvtn.utils.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * CancelOrderRequest
 * Version 1.0
 * Date: 15/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 15/11/2024        NGUYEN             create
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderRequest {
    private UUID orderId;
    private String reason;
}
