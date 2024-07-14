package com.lvtn.order.repository;

import com.lvtn.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomerId(Integer id);
}
