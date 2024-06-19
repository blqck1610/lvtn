package com.lvtn.clients.product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseResponse {
    private Integer productId;
    private String productName;
    private String brandName;
    private String description;
    private String categoryName;
    private Double price;
    private Integer quantity;
    private String imageUrl;
}
