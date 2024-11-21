package com.lvtn.clients.inventory;

import com.lvtn.utils.constant.ApiEndpoint;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.order.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * InventoryClient
 * Version 1.0
 * Date: 18/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/11/2024        NGUYEN             create
 */
@FeignClient(value = "INVENTORY-SERVICE", path = "/api/v1/internal/inventory")
public interface InventoryClient {

    @PutMapping
    public ApiResponse<Object> updateInventory(@RequestBody List<ItemDto> request);

    @PostMapping(ApiEndpoint.CANCEL)
    void cancelUpdateInventory(List<ItemDto> items);
}
