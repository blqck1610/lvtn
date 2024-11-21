package com.lvtn.utils.dto.payment;

import com.lvtn.utils.common.PaymentStatus;

import java.util.Date;

public class TransactionDTO {
    private String amount;
    private PaymentStatus status;
    private String bankCode;
    private String responseCode;
    private String orderInfo;
    private Date payDate;
}
