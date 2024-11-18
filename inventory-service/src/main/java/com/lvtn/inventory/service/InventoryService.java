package com.lvtn.inventory.service;

import com.lvtn.utils.dto.order.OrderDto;
import org.springframework.transaction.annotation.Transactional;

public interface InventoryService {
    void updateInventory(OrderDto request);

    @Transactional
    void cancelUpdateInventory(OrderDto orderDto);
}
