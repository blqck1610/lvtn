package com.lvtn.clients.notification;

import com.lvtn.clients.product.PurchaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConfirmation {
    private String orderReference;
    private long totalAmount;
    private String paymentMethod;
    private List<PurchaseResponse> products;


}
