package com.lvtn.order.controller;


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
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderRequest orderRequest
    ) {

        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }
    @GetMapping(value = "/find-all")
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }


    @GetMapping(value = "/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") Integer orderId) {
        return  ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    @GetMapping(value = "/test")
    public ResponseEntity<OrderResponse> test(HttpServletRequest request) throws UnsupportedEncodingException {
        return  ResponseEntity.ok(orderService.test(request));
    }

}
