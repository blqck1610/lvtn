package com.lvtn.payment.controller;

import com.lvtn.clients.payment.PaymentRequest;
import com.lvtn.payment.dto.PaymentResponseDTO;
import com.lvtn.payment.entity.Transaction;
import com.lvtn.payment.service.PaymentService;
import com.lvtn.payment.vnpay.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    private final PaymentService paymentService;
    private final VNPayService vnpayservice;


    @PostMapping(value = "/create-payment/vnpay")
    public PaymentResponseDTO createVNPayPayment(HttpServletRequest request, @RequestBody PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        return vnpayservice.createVNPayPayment(request, paymentRequest);
    }

    @GetMapping("/transaction-info/vnpay")
    public Object transactionVNPay(
            @RequestParam(value = "vnp_Amount", required = false) String amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_ResponseCode", required = false) String responseCode,
            @RequestParam(value = "vnp_TransactionStatus", required = false) String transactionStatus,
            @RequestParam(value = "vnp_OrderInfo", required = false) String orderInfo,
            @RequestParam(value = "vnp_PayDate", required = false) String payDateString,
            @RequestParam(value = "vnp_TxnRef", required = false) String orderId

    ) throws ParseException {
        Date payDate = formatter.parse(payDateString);
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        if (responseCode.equals("00")) {
            paymentResponseDTO.setStatus("OK");
            paymentResponseDTO.setMessage("successfully");

            Transaction transaction = Transaction.builder()
                    .responseCode(responseCode)
                    .amount(amount)
                    .transactionStatus(transactionStatus)
                    .bankCode(bankCode)
                    .orderId(orderId)
                    .orderInfo(orderInfo)
                    .build();

            paymentService.saveTransaction(transaction);
            log.info("saved transaction {}", transaction);

        } else{
            paymentResponseDTO.setStatus("FAILED");
            paymentResponseDTO.setMessage("failed");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:5173/payment-successful"));
        return headers;
    }
}
