package com.lvtn.payment.controller;

import com.lvtn.payment.entity.Transaction;
import com.lvtn.payment.service.PaymentService;
import com.lvtn.payment.vnpay.service.VNPayService;
import com.lvtn.utils.constant.ApiEndpoint;
import com.lvtn.utils.dto.payment.PaymentRequest;
import com.lvtn.utils.dto.payment.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
@RequestMapping(value = ApiEndpoint.BASE_API + ApiEndpoint.PAYMENT + ApiEndpoint.VNPAY)
@RequiredArgsConstructor
public class VNPayController {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    private final PaymentService paymentService;
    private final VNPayService vnpayservice;


    @PostMapping
    public PaymentResponse createVNPayPayment(HttpServletRequest request, @RequestBody PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        return vnpayservice.createVNPayPayment(request, paymentRequest);
    }

    @GetMapping(ApiEndpoint.TRANSACTION)
    public Object transactionVNPay(
            @RequestParam(value = "vnp_Amount", required = false) String amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bank,
            @RequestParam(value = "vnp_TransactionNo", required = false) String transactionNumber,
            @RequestParam(value = "vnp_ResponseCode", required = false) String responseCode,
            @RequestParam(value = "vnp_TransactionStatus", required = false) String transactionStatus,
            @RequestParam(value = "vnp_OrderInfo", required = false) String transactionInfo,
            @RequestParam(value = "vnp_PayDate", required = false) String payDateString,
            @RequestParam(value = "vnp_TxnRef", required = false) String transactionReference

    ) throws ParseException {
        Date payDate = formatter.parse(payDateString);
        PaymentResponse paymentResponseDTO = new PaymentResponse();
        if (responseCode.equals("00")) {
            Transaction transaction = Transaction.builder()
                    .responseCode(responseCode)
                    .amount(amount)
                    .transactionNumber(transactionNumber)
                    .bank(bank)
                    .transactionReference(transactionReference)
                    .transactionInfo(transactionInfo)
                    .payDate(payDate)
                    .transactionResponseCode(transactionStatus)
                    .build();

            paymentService.saveTransaction(transaction);
            log.info("saved transaction {}", transaction);

        } else {

        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:5173/payment-successful"));
        return headers;
    }
}
