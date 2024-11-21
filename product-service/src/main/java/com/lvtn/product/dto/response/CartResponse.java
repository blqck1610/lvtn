package com.lvtn.product.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * CartResponse
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
public class CartResponse {
    private List<ItemResponse> items;
    private Double totalAmount;
}
