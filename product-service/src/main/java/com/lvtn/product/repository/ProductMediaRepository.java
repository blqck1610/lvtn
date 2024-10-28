package com.lvtn.product.repository;

import com.lvtn.product.entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface ProductMediaRepository extends JpaRepository<ProductMedia, UUID> {
}
