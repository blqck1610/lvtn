package com.lvtn.order.dto;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        Integer quantity) {
}
