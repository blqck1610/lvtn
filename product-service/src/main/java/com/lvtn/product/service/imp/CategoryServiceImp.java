package com.lvtn.product.service.imp;

import com.lvtn.product.entity.Category;
import com.lvtn.product.repository.CategoryRepository;
import com.lvtn.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
