package com.lvtn.search.service.imp;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import com.lvtn.search.document.Product;
import com.lvtn.search.service.SearchService;
import com.lvtn.utils.constant.CommonField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SearchServiceImp
 * Version 1.0
 * Date: 01/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 01/11/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class SearchServiceImp implements SearchService {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<String> autoComplete(String prefix) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(
                        q -> q.match(match -> match.field(CommonField.NAME)
                                .operator(Operator.Or)
                                .query(prefix)
                        )
                )
                .build();
        SearchHits<Product> result = elasticsearchOperations.search(query, Product.class);
        List<Product> products = result.stream().map(SearchHit::getContent).toList();
        return products.stream().map(Product::getName).toList();
    }
}
