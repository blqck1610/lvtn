package com.lvtn.utils.dto.order;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        Integer quantity) {
}
