package com.lvtn.product.controller;

import com.lvtn.product.dto.request.AddItemRequest;
import com.lvtn.product.dto.request.UpdateItemRequest;
import com.lvtn.product.dto.response.CartResponse;
import com.lvtn.product.service.CartService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lvtn.utils.constant.ApiEndpoint.*;

/**
 * CartController
 * Version 1.0
 * Date: 11/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/11/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = BASE_API + CART)
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ApiResponse<CartResponse> getCart() {
        return ResponseUtil.getApiResponse(HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                cartService.getCartResponse());
    }

    @PostMapping(value = ITEM)
    public ApiResponse<CartResponse> addItem(@RequestBody AddItemRequest request) {
        return ResponseUtil.getApiResponse(HttpStatus.OK.value(),
                SuccessMessage.CREATED_SUCCESS.getMessage(),
                cartService.addItem(request));
    }

    @PutMapping(value = ITEM)
    public ApiResponse<CartResponse> updateItem(@RequestBody UpdateItemRequest request) {
        return ResponseUtil.getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.UPDATED_SUCCESS.getMessage(),
                cartService.updateItem(request)
        );
    }

    @DeleteMapping(value = ITEM + ID)
    public ApiResponse<CartResponse> deleteItem(@PathVariable String id) {
        cartService.deleteItem(id);
        return ResponseUtil.getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.DELETE_SUCCESS.getMessage(),
                null
        );
    }

}
