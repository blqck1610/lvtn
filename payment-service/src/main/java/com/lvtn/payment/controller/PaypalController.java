package com.lvtn.payment.controller;

import com.lvtn.payment.paypal.PaypalService;
import com.lvtn.utils.constant.ApiEndpoint;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

/**
 * PaypalController
 * Version 1.0
 * Date: 05/02/2025
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 05/02/2025        NGUYEN             create
 */
@Controller
@RequestMapping(value = ApiEndpoint.BASE_API + ApiEndpoint.PAYMENT + ApiEndpoint.PAYPAL)
@RequiredArgsConstructor
@Slf4j
public class PaypalController {
    private final PaypalService paypalService;

    @GetMapping("/")
    public String home(){
        return "index";
    }
    @PostMapping(ApiEndpoint.CREATE)
    public RedirectView createPayment() {
        try {
            String cancelUrls = "http://localhost:8080/api/v1/payment/paypal/cancel";
            String successUrl = "http://localhost:8080/api/v1/payment/paypal/success";
            Payment payment = paypalService.createPayment(
                    10.0,
                    "USD",
                    "paypal",
                    "sale",
                    "payment description",
                    cancelUrls,
                    successUrl
            );

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return new RedirectView(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error("error occurred: {}", e);
        }
        return new RedirectView("/error");
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error("error occurred: {}" + e);
        }
        return "paymentSuccess";
    }

    @GetMapping("/cancel")
    public String paymentCancel(){
        return "paymentCancel";
    }

    @GetMapping("/error")
    public String paymentError(){
        return "paymentError";
    }
}
