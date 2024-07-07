package com.lvtn.product.repository;

import com.lvtn.product.entity.Brand;
import com.lvtn.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Optional<Brand> findByName(String name);

}
