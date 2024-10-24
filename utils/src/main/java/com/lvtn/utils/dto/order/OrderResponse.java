package com.lvtn.utils.dto.order;

import com.lvtn.utils.common.PaymentMethod;
import com.lvtn.utils.common.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer id;
    private long totalAmount;
    private PaymentMethod paymentMethod;
    private Integer userId;
    private String status;
    private PaymentStatus paymentStatus;
    private List<OrderLineResponse> items;
    private LocalDateTime createdDate;
}
