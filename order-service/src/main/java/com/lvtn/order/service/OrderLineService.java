package com.lvtn.order.service;

import com.lvtn.order.dto.OrderLineRequest;
import com.lvtn.order.dto.OrderLineResponse;
import com.lvtn.order.entity.OrderLine;
import com.lvtn.order.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final Mapper mapper;


//    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
//            return orderLineRepository.saveAndFlush(mapper.toOrderLine(orderLineRequest)).getId();
//    }

    public List<OrderLineResponse> findByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId).stream().map(mapper::toOrderLineResponse).collect(Collectors.toList());
    }
}
