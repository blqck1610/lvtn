package com.lvtn.order.controller;

import com.lvtn.utils.dto.order.OrderLineResponse;
import com.lvtn.order.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/order-lines")
@RequiredArgsConstructor
public class OrderLineController {
    private final OrderLineService orderLineService;

    @GetMapping(value = "/order/{order-id}")
    public List<OrderLineResponse> getByOrderId(@PathVariable("order-id") Integer orderId){
        return orderLineService.findByOrderId(orderId);
    }
}
