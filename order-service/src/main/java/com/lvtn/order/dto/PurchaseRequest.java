package com.lvtn.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

public class PurchaseRequest {
    @NotNull(message = "product id is mandatory")
    private Integer productId;
    @NotNull(message = "quantity is mandatory")
    private Integer quantity;

}
