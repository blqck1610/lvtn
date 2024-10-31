package com.lvtn.product.repository;

import com.lvtn.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("select p from Product p where p.id = :id and p.isDelete != true ")
    Optional<Product> findProductById(@Param("id") UUID id);
}
