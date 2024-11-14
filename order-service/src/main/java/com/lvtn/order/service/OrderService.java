package com.lvtn.order.service;

import com.lvtn.order.dto.request.CreateOrderRequest;
import com.lvtn.order.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
}
