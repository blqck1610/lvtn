package com.lvtn.order.controller;


import com.lvtn.order.dto.OrderRequest;
import com.lvtn.order.entity.Order;
import com.lvtn.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }


    @GetMapping(value = "/test")
    public ResponseEntity<Integer> createOrder(
            ) {

        return ResponseEntity.ok(orderService.testProducer());
    }

}
