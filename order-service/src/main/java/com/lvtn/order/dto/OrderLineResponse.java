package com.lvtn.order.dto;


import lombok.Builder;

@Builder
public class OrderLineResponse {
    Integer id;
    int quantity;

}
