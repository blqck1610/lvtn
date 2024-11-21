package com.lvtn.inventory.service.imp;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.inventory.entity.Inventory;
import com.lvtn.inventory.repository.IInventoryRepository;
import com.lvtn.inventory.service.InventoryService;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.inventory.InventoryDto;
import com.lvtn.utils.dto.order.CancelOrderRequest;
import com.lvtn.utils.dto.order.ItemDto;
import com.lvtn.utils.dto.order.CreateOrderRequest;
import com.lvtn.utils.exception.BaseException;
import com.lvtn.utils.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * InventoryImp
 * Version 1.0
 * Date: 18/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/11/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class InventoryServiceImp implements InventoryService {
    private final IInventoryRepository iInventoryRepository;
    private final RabbitMQMessageProducer producer;


    @Override
    @Transactional
    public void updateInventory(CreateOrderRequest request) {
        for (ItemDto itemDto : request.getItems()) {
            try {
                Inventory inventory = iInventoryRepository.findByProductId(itemDto.getProductId()).orElseThrow(() -> new BaseException(ErrorCode.INVENTORY_NOT_FOUND.getCode(), ErrorCode.INVENTORY_NOT_FOUND.getMessage()));
                int quantity = inventory.getAvailableQuantity() - itemDto.getQuantity();
                if (quantity < 0) {
                    throw new BaseException(ErrorCode.OUT_OF_STOCK.getCode(), ErrorCode.OUT_OF_STOCK.getMessage());
                }
                inventory.setAvailableQuantity(quantity);
                iInventoryRepository.save(inventory);

            } catch (BaseException e) {
                CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
                cancelOrderRequest.setOrderId(request.getId());
                cancelOrderRequest.setReason(e.getMessage());
            }
        }
    }

    @Override
    public ApiResponse<Object> updateInventory(List<ItemDto> request) {
        for (ItemDto itemDto : request) {
            Inventory inventory = iInventoryRepository.findByProductId(itemDto.getProductId()).orElseThrow(() -> new BaseException(ErrorCode.INVENTORY_NOT_FOUND.getCode(), ErrorCode.INVENTORY_NOT_FOUND.getMessage()));
            int quantity = inventory.getAvailableQuantity() - itemDto.getQuantity();
            if (quantity < 0) {
                return ResponseUtil.getApiResponse(
                        ErrorCode.BAD_REQUEST.getCode(), ErrorCode.OUT_OF_STOCK.getMessage(), null
                );
            }
            inventory.setAvailableQuantity(quantity);
            iInventoryRepository.save(inventory);
        }
        return ResponseUtil.getApiResponse(
                HttpStatus.OK.value(),
                SuccessMessage.UPDATED_SUCCESS.getMessage(), null
        );
    }

    @Transactional
    @Override
    public void cancelUpdateInventory(CreateOrderRequest orderDto) {
        for (ItemDto itemDto : orderDto.getItems()) {
            Inventory inventory = iInventoryRepository.findByProductId(itemDto.getProductId()).orElseThrow(() -> new BaseException(ErrorCode.INVENTORY_NOT_FOUND.getCode(), ErrorCode.INVENTORY_NOT_FOUND.getMessage()));
            int quantity = inventory.getAvailableQuantity() + itemDto.getQuantity();
            inventory.setAvailableQuantity(quantity);
            iInventoryRepository.save(inventory);
        }
    }


}
