package com.lvtn.order.controller;

import com.lvtn.order.service.OrderService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.order.CreateOrderRequest;
import com.lvtn.utils.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lvtn.utils.constant.ApiEndpoint.BASE_API;
import static com.lvtn.utils.constant.ApiEndpoint.ORDER;

/**
 * OrderController
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = BASE_API + ORDER)
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ApiResponse<CreateOrderRequest> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseUtil.getApiResponse(HttpStatus.OK.value(),
                SuccessMessage.CREATED_SUCCESS.getMessage(),
                orderService.createOrder(request));
    }
}
