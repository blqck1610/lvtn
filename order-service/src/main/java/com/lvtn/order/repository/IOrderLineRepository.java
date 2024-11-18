package com.lvtn.order.repository;

import com.lvtn.order.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrderLineRepository extends JpaRepository<OrderLine, UUID> {
}
