package com.lvtn.product.filter;

import lombok.Data;

/**
 * ProductFilter
 * Version 1.0
 * Date: 31/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 31/10/2024        NGUYEN             create
 */
@Data
public class ProductFilter {
    private String keyword;
    private String brandName;
    private String categoryName;
    private Double priceMin;
    private Double priceMax;
    private Boolean isSales;
}
