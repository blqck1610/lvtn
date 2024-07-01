package com.lvtn.order.dto;

import com.lvtn.clients.product.PurchaseRequest;
import com.lvtn.order.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Integer id;
    private String reference;
    @Positive(message = "order amount should be positive")
    private long amount;
    @NotNull(message = "payment method should be precised")
    private PaymentMethod paymentMethod;
    @NotNull(message = "payment method should be present")
    @NotEmpty(message = "payment method should be present")
    @NotBlank(message = "payment method should be present")
    private Integer customerId;

    @NotEmpty(message = "You should add at least purchase one product")
    List<PurchaseRequest> products;
}
