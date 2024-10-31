package com.lvtn.product.controller;


import com.lvtn.product.dto.request.CreateNewProductRequest;
import com.lvtn.product.dto.request.UpdateProductRequest;
import com.lvtn.product.dto.response.ProductResponse;
import com.lvtn.product.filter.ProductFilter;
import com.lvtn.product.service.ProductService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.page.PagingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.lvtn.utils.constant.ApiEndpoint.*;
import static com.lvtn.utils.util.ResponseUtil.getApiResponse;

@RestController
@RequestMapping(value = BASE_API + PRODUCT)
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> saveProduct(@RequestBody CreateNewProductRequest request) {
        return getApiResponse(HttpStatus.CREATED.value(),
                SuccessMessage.CREATED_SUCCESS.getMessage(),
                productService.saveProduct(request)
        );
    }

    @GetMapping(value = ID)
    public ApiResponse<ProductResponse> getProduct(@PathVariable("id") String id) {
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                productService.getProductDto(id)
        );
    }

    @PutMapping
    public ApiResponse<ProductResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                productService.updateProduct(request)
        );
    }

    @DeleteMapping(value = ID)
    public ApiResponse<ProductResponse> delProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.DELETE_SUCCESS.getMessage(),
                null
        );
    }

    @PostMapping(value = VIEW_LIST)
    public ApiResponse<Page<ProductResponse>> getPageProduct(@RequestBody PagingRequest<ProductFilter> pagingRequest){
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                productService.getPageProduct(pagingRequest)
        );

    }

}
