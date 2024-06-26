package com.lvtn.clients.product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {
    private Integer productId;
    private String productName;
    private String brandName;
    private String categoryName;
    private Double price;
    private String imageUrl;
}
