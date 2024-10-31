package com.lvtn.product.service;

import com.lvtn.product.entity.Category;

import java.util.UUID;

public interface CategoryService {
    Category getCategoryByName(String categoryName);

    Category getReferenceById(UUID uuid);
}
