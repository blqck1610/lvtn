package com.lvtn.product.controller;


import com.lvtn.product.dto.request.CreateNewProductRequest;
import com.lvtn.product.dto.request.CreateReviewRequest;
import com.lvtn.product.dto.request.UpdateProductRequest;
import com.lvtn.product.dto.request.UpdateReviewRequest;
import com.lvtn.product.dto.response.ProductResponse;
import com.lvtn.product.dto.response.ReviewResponse;
import com.lvtn.product.filter.IdFilter;
import com.lvtn.product.filter.ProductFilter;
import com.lvtn.product.service.ProductService;
import com.lvtn.product.service.ReviewService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.constant.Common;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.page.PagingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lvtn.utils.constant.ApiEndpoint.*;
import static com.lvtn.utils.util.ResponseUtil.getApiResponse;

@RestController
@RequestMapping(value = BASE_API + PRODUCT)
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    // PUBLIC
    @GetMapping(value = PUBLIC + ID)
    public ApiResponse<ProductResponse> getProduct(@PathVariable(Common.ID) String id) {
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                productService.getProductDto(id)
        );
    }

    @PostMapping(value = PUBLIC + VIEW_LIST)
    public ApiResponse<Page<ProductResponse>> getPageProduct(@RequestBody PagingRequest<ProductFilter> pagingRequest) {
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                productService.getPageProduct(pagingRequest)
        );
    }

    @GetMapping(value = PUBLIC + AUTO_COMPLETE)
    public ApiResponse<List<String>> autoComplete(@RequestParam(name = Common.PREFIX) String prefix) {
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.OK.getMessage(), productService.autoComplete(prefix));
    }

    @PostMapping(value = PUBLIC + REVIEW + VIEW_LIST)
    public ApiResponse<List<ReviewResponse>> getListReviews(@RequestBody PagingRequest<IdFilter> pagingRequest) {
        return null;
    }

    //    ADMIN
    @PostMapping(value = ADMIN)
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ApiResponse<ProductResponse> saveProduct(@RequestBody CreateNewProductRequest request) {
        return getApiResponse(HttpStatus.CREATED.value(),
                SuccessMessage.CREATED_SUCCESS.getMessage(),
                productService.saveProduct(request)
        );
    }

    @PutMapping(value = ADMIN)
    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    public ApiResponse<ProductResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                productService.updateProduct(request)
        );
    }

    @DeleteMapping(value = ADMIN + ID)
    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    public ApiResponse<ProductResponse> delProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.DELETE_SUCCESS.getMessage(),
                null
        );
    }

//    USER

    @PostMapping(value = REVIEW)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse<ReviewResponse> createReview(@RequestBody CreateReviewRequest request) {
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.CREATED_SUCCESS.getMessage(), reviewService.createReview(request));
    }

    @PutMapping(value = REVIEW)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse<ReviewResponse> updateReview(@RequestBody UpdateReviewRequest request) {
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.UPDATED_SUCCESS.getMessage(), reviewService.updateReview(request));
    }

    @DeleteMapping(value = REVIEW + ID)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse<ReviewResponse> deleteReview(@PathVariable String id) {
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.DELETE_SUCCESS.getMessage(), reviewService.deleteReview(id));
    }

    @PostMapping(value = REVIEW + VIEW_LIST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse<Page<ReviewResponse>> getPageReview(@RequestBody PagingRequest<IdFilter> request) {
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.GET_SUCCESS.getMessage(), reviewService.getPageReview(request));
    }


}
