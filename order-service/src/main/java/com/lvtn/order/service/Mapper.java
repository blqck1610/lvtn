package com.lvtn.order.service;

import com.lvtn.order.dto.OrderLineRequest;
import com.lvtn.order.dto.OrderRequest;
import com.lvtn.order.entity.Order;
import com.lvtn.order.entity.OrderLine;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Builder
@Service
public class Mapper {

    public Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .id(orderRequest.getId())
                .customerId(orderRequest.getCustomerId())
                .reference(orderRequest.getReference())
                .paymentMethod(orderRequest.getPaymentMethod())
                .totalAmount(orderRequest.getAmount())
                .build();
    }

    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return  OrderLine.builder()
                .id(orderLineRequest.id())
                .order( Order.builder().id(orderLineRequest.orderId()).build())
                .productId(orderLineRequest.productId())
                .quantity(orderLineRequest.quantity())

                .build();
    }
}
