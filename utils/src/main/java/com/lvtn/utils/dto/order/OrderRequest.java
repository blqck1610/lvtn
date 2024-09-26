package com.lvtn.utils.dto.order;

import com.lvtn.clients.product.PurchaseRequest;
import com.lvtn.order.entity.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull(message = "payment method should be precised")
    private String paymentMethod;
    @NotEmpty(message = "You should add at least purchase one product")
    private List<PurchaseRequest> products;
}
