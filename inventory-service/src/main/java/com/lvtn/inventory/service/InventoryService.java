package com.lvtn.inventory.service;

import com.lvtn.utils.dto.inventory.InventoryDto;
import com.lvtn.utils.dto.order.ItemDto;
import com.lvtn.utils.dto.order.OrderDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InventoryService {
    void updateInventory(OrderDto request);

    @Transactional
    void cancelUpdateInventory(OrderDto orderDto);

    List<InventoryDto> getInventoryList(List<ItemDto> request);
}
