package com.lvtn.utils.dto.payment;

import com.lvtn.utils.common.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse implements Serializable {
    private PaymentMethod paymentMethod;
    private String URL;

}
