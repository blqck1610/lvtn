package com.lvtn.clients.notification;

import com.lvtn.clients.product.PurchaseRequest;
import com.lvtn.clients.product.PurchaseResponse;
import com.lvtn.clients.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
