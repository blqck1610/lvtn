package com.lvtn.product.service.imp;

import com.lvtn.product.entity.Brand;
import com.lvtn.product.repository.BrandRepository;
import com.lvtn.product.service.BrandService;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        return brandRepository.findByName(brandName).orElseThrow(() -> new BaseException(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BRAND_NOT_FOUND.getMessage()));
    }

    @Override
    public Brand getReferenceById(UUID uuid) {
        return brandRepository.getReferenceById(uuid);
    }
}
