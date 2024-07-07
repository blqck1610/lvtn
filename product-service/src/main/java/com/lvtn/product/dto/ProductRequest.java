package com.lvtn.product.dto;

import lombok.Data;

@Data
public class ProductRequest {
    String productName;
    String brand;
    String category;
    String gender;
    String description;
    Integer availableQuantity;
    double price;
    String imageUrl;
}
