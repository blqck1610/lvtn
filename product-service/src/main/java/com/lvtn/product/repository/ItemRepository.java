package com.lvtn.product.repository;

import com.lvtn.product.dto.response.ItemResponse;
import com.lvtn.product.entity.Cart;
import com.lvtn.product.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * ItemRepository
 * Version 1.0
 * Date: 11/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/11/2024        NGUYEN             create
 */
public interface ItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findAllByCart(Cart cart);
}
