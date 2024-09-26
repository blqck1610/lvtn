package com.lvtn.utils.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
