package com.lvtn.order.service.imp;

import com.lvtn.order.dto.request.CreateOrderRequest;
import com.lvtn.order.dto.response.OrderResponse;
import com.lvtn.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OrderServiceImp
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@Service
public class OrderServiceImp implements OrderService {

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
//        process

        return null;
    }
}
