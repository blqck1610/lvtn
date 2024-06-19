package com.lvtn.order.service;

import com.lvtn.order.dto.OrderLineRequest;
import com.lvtn.order.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final Mapper mapper;


    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
            return orderLineRepository.saveAndFlush(mapper.toOrderLine(orderLineRequest)).getId();
    }
}
