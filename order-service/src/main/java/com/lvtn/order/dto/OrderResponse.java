package com.lvtn.order.dto;

import com.lvtn.order.entity.OrderLine;
import com.lvtn.order.entity.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public class OrderResponse {
    private Integer id;
    private String reference;
    private long totalAmount;
    private PaymentMethod paymentMethod;
    private Integer userId;
    private String status;
    private LocalDateTime createdDate;
}
