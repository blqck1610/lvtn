package com.lvtn.product.dto.request;

import lombok.Data;

/**
 * UpdateItemRequest
 * Version 1.0
 * Date: 11/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/11/2024        NGUYEN             create
 */
@Data
public class UpdateItemRequest {
    private String id;
    private int quantity;
}
