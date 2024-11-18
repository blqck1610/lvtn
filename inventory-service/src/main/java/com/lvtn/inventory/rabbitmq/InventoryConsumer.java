package com.lvtn.inventory.rabbitmq;

import com.lvtn.inventory.service.InventoryService;
import com.lvtn.utils.dto.order.OrderDto;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMqConsumer
 * Version 1.0
 * Date: 18/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/11/2024        NGUYEN             create
 */
@Component
@Slf4j
@AllArgsConstructor
public class InventoryConsumer {

    private final InventoryService inventoryService;

    @RabbitListener(queues = "${rabbitmq.queue.inventory}")
    @Observed
    public void consumerNotification(OrderDto request) {
        inventoryService.updateInventory(request);
    }
}

