package com.lvtn.utils.dto.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequest {
    @NotEmpty(message = "username must not be empty")
    private String username;
    @NotNull(message = "product id must not be empty")
    private Integer productId;
    @NotNull(message = "quantity must not be empty")
    private Integer quantity;
}