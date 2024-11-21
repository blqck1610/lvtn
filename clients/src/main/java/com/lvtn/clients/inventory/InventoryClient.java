package com.lvtn.clients.inventory;

import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.inventory.InventoryDto;
import com.lvtn.utils.dto.order.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ApiResponse<List<InventoryDto>> getInventoryList(@RequestBody List<ItemDto> request);

}
