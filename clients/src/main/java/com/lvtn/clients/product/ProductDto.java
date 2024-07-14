package com.lvtn.clients.product;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProductDto {
    private Integer productId;
    private String productName;
    private String brandName;
    private String gender;
    private String categoryName;
    private Double price;
    private Integer availableQuantity;
    private String imageUrl;
}
