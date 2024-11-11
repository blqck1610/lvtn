package com.lvtn.product.service;

import com.lvtn.product.dto.request.AddItemRequest;
import com.lvtn.product.dto.request.UpdateItemRequest;
import com.lvtn.product.dto.response.CartResponse;
import org.springframework.transaction.annotation.Transactional;

public interface CartService {
    CartResponse getCartResponse();

    CartResponse addItem(AddItemRequest request);

    @Transactional
    void deleteItem(String id);

    @Transactional
    CartResponse updateItem(UpdateItemRequest request);
}
