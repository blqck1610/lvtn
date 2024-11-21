package com.lvtn.clients.inventory;

import org.springframework.cloud.openfeign.FeignClient;

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
@FeignClient(value = "INVENTORY-SERVICE", path = "/api/v1/inventory")
public interface InventoryClient {

}
