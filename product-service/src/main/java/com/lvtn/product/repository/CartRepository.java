package com.lvtn.product.repository;

import com.lvtn.product.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query("SELECT c FROM Cart c WHERE c.username = :username and c.isDelete != true")
    Cart getByUsername(@Param("username") String username);
}
