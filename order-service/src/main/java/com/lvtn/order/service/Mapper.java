package com.lvtn.order.service;

import com.lvtn.utils.dto.product.PurchaseResponse;
import com.lvtn.utils.dto.order.OrderLineResponse;
import com.lvtn.utils.dto.order.OrderResponse;
import com.lvtn.order.entity.Order;
import com.lvtn.order.entity.OrderLine;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Data
@Builder
@Service
public class Mapper {

//    public Order toOrder(OrderRequest orderRequest) {
//        return Order.builder()
////                .id(orderRequest.getId())
////                .customerId(orderRequest.getCustomerId())
////                .reference(orderRequest.getReference())
//                .paymentMethod(orderRequest.getPaymentMethod())
////                .totalAmount(orderRequest.getAmount())
//                .build();
//    }

    public OrderLine toOrderLine(PurchaseResponse purchaseResponse) {
        return  OrderLine.builder()

                .productId(purchaseResponse.getProductId())
                .quantity(purchaseResponse.getQuantity())
                .price(purchaseResponse.getPrice())
                .brandName(purchaseResponse.getBrandName())
                .productName(purchaseResponse.getProductName())
                .categoryName(purchaseResponse.getCategoryName())
                .description(purchaseResponse.getDescription())
                .imageUrl(purchaseResponse.getImageUrl())
                .build();
    }
    OrderLineResponse toOrderLineResponse(OrderLine orderLine){
        return OrderLineResponse.builder()
                .productId(orderLine.getProductId())
                .quantity(orderLine.getQuantity())
                .price(orderLine.getPrice())
                .brandName(orderLine.getBrandName())
                .productName(orderLine.getProductName())
                .categoryName(orderLine.getCategoryName())
                .description(orderLine.getDescription())
                .imageUrl(orderLine.getImageUrl())
                .build();

    }

    public OrderResponse toOrderResponse(Order order){
        return OrderResponse.builder()
                .createdDate(order.getCreatedDate())
                .status(order.getStatus().toString())
                .userId(order.getCustomerId())
                .id(order.getId())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .totalAmount(order.getTotalAmount())
                .items(order.getOrderLines().stream().map(this::toOrderLineResponse).collect(Collectors.toList()))
                .build();
    }
}
