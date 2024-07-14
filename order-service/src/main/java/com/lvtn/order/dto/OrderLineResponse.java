package com.lvtn.order.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineResponse {
    private Integer productId;
    private String productName;
    private String brandName;
    private String description;
    private String categoryName;
    private Double price;
    private Integer quantity;
    private String imageUrl;

}
