package com.lvtn.product.controller;

import com.lvtn.product.dto.request.CreateNewProductRequest;
import com.lvtn.product.dto.request.UpdateProductRequest;
import com.lvtn.product.dto.response.ProductResponse;
import com.lvtn.product.service.ProductService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.lvtn.utils.constant.ApiEndpoint.*;
import static com.lvtn.utils.util.ResponseUtil.getApiResponse;

/**
 * AdminController
 * Version 1.0
 * Date: 04/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 04/11/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = BASE_API + ADMIN + PRODUCT)
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasPermission('ADMIN_CREATE')")
    public ApiResponse<ProductResponse> saveProduct(@RequestBody CreateNewProductRequest request) {
        return getApiResponse(HttpStatus.CREATED.value(),
                SuccessMessage.CREATED_SUCCESS.getMessage(),
                productService.saveProduct(request)
        );
    }

    @PutMapping
    @PreAuthorize("hasPermission('ADMIN_UPDATE')")
    public ApiResponse<ProductResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                productService.updateProduct(request)
        );
    }

    @DeleteMapping(value = ID)
    @PreAuthorize("hasPermission('ADMIN_DELETE')")
    public ApiResponse<ProductResponse> delProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.DELETE_SUCCESS.getMessage(),
                null
        );
    }
}
