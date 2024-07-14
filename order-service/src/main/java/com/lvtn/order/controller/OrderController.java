package com.lvtn.order.controller;


import com.lvtn.clients.payment.PaymentResponseDTO;
import com.lvtn.order.dto.OrderRequest;
import com.lvtn.order.dto.OrderResponse;
import com.lvtn.order.entity.Order;
import com.lvtn.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrder(@RequestHeader("username") String username,
                                                          @RequestBody @Valid OrderRequest orderRequest
    ) throws UnsupportedEncodingException {

        return orderService.createOrder(orderRequest, username);
    }
    @GetMapping(value = "/find-all")
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }


//    @GetMapping(value = "/{orderId}")
//    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") Integer orderId) {
//        return  ResponseEntity.ok(orderService.getOrderById(orderId));
//    }
//    @GetMapping(value = "/test")
//    public String test(HttpServletRequest request) throws UnsupportedEncodingException {
//        return  orderService.test(request);
//    }
    @GetMapping(value = "/get-all-order-for-user")
    public ResponseEntity<List<OrderResponse>> getAllOrderForUser(@RequestHeader("username") String username){
        return ResponseEntity.ok(orderService.getAllOrderForUser(username));
    }
    @GetMapping(value = "/get-order/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") Integer id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }

}
