package com.lvtn.utils.dto.order;

import com.lvtn.utils.common.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * OrderDto
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
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private UUID id;
    private UUID userId;
    private List<ItemDto> items;
    private PaymentMethod paymentMethod;
}
