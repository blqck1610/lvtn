package com.lvtn.order.service;

import com.lvtn.clients.user.UserClient;
import com.lvtn.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserClient userClient;
    public Integer createOrder(OrderRequest orderRequest){
//        todo: check the user --> openfeign

//        todo: persist order
//        todo: persist order line
//        todo: start payment process
//        todo: send order comfirm information --> notifications



    }
}
