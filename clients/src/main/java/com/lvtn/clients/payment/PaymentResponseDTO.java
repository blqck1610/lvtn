package com.lvtn.clients.payment;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentResponseDTO implements Serializable {
    private String status;
    private String message;
    private String URL;

}
