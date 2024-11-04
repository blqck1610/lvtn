package com.lvtn.product.controller;


import com.lvtn.product.dto.request.CreateNewProductRequest;
import com.lvtn.product.dto.request.UpdateProductRequest;
import com.lvtn.product.dto.response.ProductResponse;
import com.lvtn.product.filter.ProductFilter;
import com.lvtn.product.service.ProductService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.constant.Common;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.page.PagingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lvtn.utils.constant.ApiEndpoint.*;
import static com.lvtn.utils.util.ResponseUtil.getApiResponse;

@RestController
@RequestMapping(value = BASE_API + PUBLIC + PRODUCT)
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = ID)
    public ApiResponse<ProductResponse> getProduct(@PathVariable(Common.ID) String id) {
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                productService.getProductDto(id)
        );
    }

    @PostMapping(value = VIEW_LIST)
    public ApiResponse<Page<ProductResponse>> getPageProduct(@RequestBody PagingRequest<ProductFilter> pagingRequest) {
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                productService.getPageProduct(pagingRequest)
        );
    }

    @GetMapping(value = AUTO_COMPLETE)
    public ApiResponse<List<String>> autoComplete(@RequestParam(name = Common.PREFIX) String prefix){
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.OK.getMessage(), productService.autoComplete(prefix));
    }
}
