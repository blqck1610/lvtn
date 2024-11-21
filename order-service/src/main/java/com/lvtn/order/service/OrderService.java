package com.lvtn.order.service;

import com.lvtn.utils.dto.order.OrderDto;
import com.lvtn.utils.dto.order.CancelOrderRequest;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    OrderDto createOrder(OrderDto request);

    @Transactional
    void cancelOrder(CancelOrderRequest request);
}
