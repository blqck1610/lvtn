package com.lvtn.clients.product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
    @NotNull(message = "product id is mandatory")
    private Integer productId;
    @NotNull(message = "quantity is mandatory")
    private Integer quantity;

}
