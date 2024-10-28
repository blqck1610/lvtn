package com.lvtn.product.controller;


import com.lvtn.product.service.ProductService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.product.CreateNewProductRequest;
import com.lvtn.utils.dto.response.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.lvtn.utils.constant.ApiEndpoint.BASE_API;
import static com.lvtn.utils.constant.ApiEndpoint.PRODUCT;
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

    @PostMapping(value = "/test")
    public String testSaveImg(@RequestParam("file") MultipartFile file) {
        log.info("save img {} ", "test");
        return "ok";
    }

}
