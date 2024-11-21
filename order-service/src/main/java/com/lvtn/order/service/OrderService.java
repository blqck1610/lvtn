package com.lvtn.order.service;

import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.order.CancelOrderRequest;
import com.lvtn.utils.dto.order.CreateOrderResponse;
import com.lvtn.utils.dto.order.CreateOrderRequest;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);

    @Transactional
    void cancelOrder(CancelOrderRequest request);
}
