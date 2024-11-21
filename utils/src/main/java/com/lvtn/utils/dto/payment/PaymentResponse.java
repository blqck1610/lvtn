package com.lvtn.utils.dto.payment;

import com.lvtn.utils.common.PaymentMethod;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PaymentResponse implements Serializable {
    private PaymentMethod paymentMethod;
    private String URL;
}
