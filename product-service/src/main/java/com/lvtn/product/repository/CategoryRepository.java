package com.lvtn.product.repository;

import com.lvtn.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("select c from Category c where c.name = :name and c.isDelete != true")
    Optional<Category> findByName(@Param("name") String name);
}
