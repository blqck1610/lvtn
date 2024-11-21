package com.lvtn.inventory.controller;

import com.lvtn.inventory.service.InventoryService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.inventory.InventoryDto;
import com.lvtn.utils.dto.order.ItemDto;
import com.lvtn.utils.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lvtn.utils.constant.ApiEndpoint.*;

/**
 * InternalController
 * Version 1.0
 * Date: 21/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/11/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = BASE_API + INTERNAL + INVENTORY)
@Slf4j
@RequiredArgsConstructor
public class InternalController {
    private final InventoryService inventoryService;

    @GetMapping
    public ApiResponse<List<InventoryDto>> getInventoryList(@RequestBody List<ItemDto> request) {
        return ResponseUtil.getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.GET_SUCCESS.getMessage(),
                inventoryService.getInventoryList(request));
    }
}
