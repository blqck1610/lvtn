package com.lvtn.product.service;

import com.lvtn.product.dto.request.UpdateProductRequest;
import com.lvtn.product.dto.request.CreateNewProductRequest;
import com.lvtn.product.dto.response.ProductResponse;
import com.lvtn.product.filter.ProductFilter;
import com.lvtn.utils.dto.request.page.PagingRequest;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponse saveProduct(CreateNewProductRequest request);

    ProductResponse getProductDto(String id);

    ProductResponse updateProduct(UpdateProductRequest request);

    void deleteProduct(String id);

    Page<ProductResponse> getPageProduct(PagingRequest<ProductFilter> pagingRequest);
}
