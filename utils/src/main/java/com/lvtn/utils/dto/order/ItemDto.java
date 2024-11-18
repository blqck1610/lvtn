package com.lvtn.utils.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * ItemDto
 * Version 1.0
 * Date: 15/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 15/11/2024        NGUYEN             create
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private String id;
    private UUID productId;
    private Double price;
    private int quantity;
}

