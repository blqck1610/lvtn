package com.lvtn.product.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * ItemResponse
 * Version 1.0
 * Date: 11/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/11/2024        NGUYEN             create
 */
@Data
@Builder
public class ItemResponse {
    private String id;
    private ProductResponse product;
    private int quantity;
}
