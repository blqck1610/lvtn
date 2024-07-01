package com.lvtn.payment.controller;

import com.lvtn.clients.payment.PaymentRequest;
import com.lvtn.payment.dto.PaymentResponseDTO;
import com.lvtn.payment.entity.Transaction;
import com.lvtn.payment.service.PaymentService;
import com.lvtn.payment.vnpay.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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
    public ResponseEntity<PaymentResponseDTO> createVNPayPayment(HttpServletRequest request, @RequestBody PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        return ResponseEntity.ok(vnpayservice.createVNPayPayment(request, paymentRequest));
    }

    @GetMapping("/transaction-info/vnpay")
    public ResponseEntity<?> transactionVNPay(
            @RequestParam(value = "vnp_Amount", required = false) String amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_ResponseCode", required = false) String responseCode,
            @RequestParam(value = "vnp_OrderInfo", required = false) String orderInfo,
            @RequestParam(value = "vnp_PayDate", required = false) String payDateString

    ) throws ParseException {
        Date payDate = formatter.parse(payDateString);
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        if (responseCode.equals("00")) {
            paymentResponseDTO.setStatus("OK");
            paymentResponseDTO.setMessage("successfully");

            Transaction transaction = Transaction.builder()
                    .responseCode(responseCode)
                    .amount(amount)
                    .bankCode(bankCode)
                    .orderInfo(orderInfo)
                    .build();

            paymentService.saveTransaction(transaction);
            log.info("saved transaction {}", transaction);

        } else{
            paymentResponseDTO.setStatus("FAILED");
            paymentResponseDTO.setMessage("failed");
        }
        return ResponseEntity.ok(paymentResponseDTO);

    }
}
