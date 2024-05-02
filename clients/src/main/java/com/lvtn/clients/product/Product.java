package com.lvtn.clients.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private String productName;
    private String brandName;
    private String categoryName;
    private Double price;
    private String imageUrl;
}