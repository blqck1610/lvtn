package com.lvtn.product.service.imp;

import com.lvtn.product.entity.Category;
import com.lvtn.product.repository.CategoryRepository;
import com.lvtn.product.service.CategoryService;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * CategoryServiceImp
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
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(()->new BaseException(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.CATEGORY_NOT_FOUND.getMessage()));
    }

    @Override
    public Category getReferenceById(UUID uuid) {
        return categoryRepository.getReferenceById(uuid);
    }
}
