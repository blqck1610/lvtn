package com.lvtn.search.repository;

import com.lvtn.search.document.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, UUID> {
}
