package com.lvtn.order.repository;

import com.lvtn.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * IOrderRepository
 * Version 1.0
 * Date: 15/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 15/11/2024        NGUYEN             create
 */
public interface IOrderRepository extends JpaRepository<Order, UUID> {
}
