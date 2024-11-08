package com.lvtn.product.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * SaleReposne
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@Data
public class SaleResponse {
    private Integer value;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
