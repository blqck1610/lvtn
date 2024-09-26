package com.lvtn.order.controller;


import com.lvtn.utils.dto.order.OrderRequest;
import com.lvtn.utils.dto.order.OrderResponse;
import com.lvtn.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public Object createOrder(@RequestHeader("username") String username,
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
    public List<OrderResponse> getAllOrderForUser(@RequestHeader("username") String username){
        return orderService.getAllOrderForUser(username);
    }
    @GetMapping(value = "/get-order/{id}")
    public OrderResponse getOrder(@PathVariable("id") Integer id){
        return orderService.getOrder(id);
    }

}
