package com.lvtn.product.repository;

import com.lvtn.product.entity.Brand;
import com.lvtn.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    @Query("SELECT b FROM Brand b WHERE b.name = :name and b.isDelete != true ")
    Optional<Brand> findByName(@Param("name") String name);
}
