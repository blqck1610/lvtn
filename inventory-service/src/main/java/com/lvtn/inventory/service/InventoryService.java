package com.lvtn.inventory.service;

import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.order.CreateOrderRequest;
import com.lvtn.utils.dto.order.ItemDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InventoryService {
    void updateInventory(CreateOrderRequest request);

    ApiResponse<Object> updateInventory(List<ItemDto> request);

    @Transactional
    void cancelUpdateInventory(CreateOrderRequest orderDto);

}
