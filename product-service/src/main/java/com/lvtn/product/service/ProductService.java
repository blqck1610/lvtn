package com.lvtn.product.service;

import com.lvtn.utils.dto.request.product.CreateNewProductRequest;
import com.lvtn.utils.dto.response.product.ProductResponse;

public interface ProductService {
    ProductResponse saveProduct(CreateNewProductRequest request);
}
