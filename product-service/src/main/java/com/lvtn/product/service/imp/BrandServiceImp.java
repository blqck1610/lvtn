package com.lvtn.product.service.imp;

import com.lvtn.product.entity.Brand;
import com.lvtn.product.repository.BrandRepository;
import com.lvtn.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * BrandServiceImp
 * Version 1.0
 * Date: 28/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 28/10/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class BrandServiceImp implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Brand getBrandByName(String brandName) {
        return null;
    }
}
