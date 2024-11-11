package com.lvtn.product.dto.request;

import lombok.Data;

/**
 * AddItemRequest
 * Version 1.0
 * Date: 11/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/11/2024        NGUYEN             create
 */
@Data
public class AddItemRequest {
    private String productId;
    private int quantity;
}
