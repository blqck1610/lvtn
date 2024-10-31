package com.lvtn.product.service;

import com.lvtn.product.entity.Brand;

import java.util.UUID;

/**
 * BrandService
 * Version 1.0
 * Date: 28/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 28/10/2024        NGUYEN             create
 */
public interface BrandService {
    Brand getBrandByName(String brandName);

    Brand getReferenceById(UUID uuid);
}
