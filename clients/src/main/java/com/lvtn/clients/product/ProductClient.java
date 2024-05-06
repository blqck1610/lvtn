package com.lvtn.clients.product;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = "PRODUCT-SERVICE", path = "/api/v1/product")
public interface ProductClient {
    @PostMapping(value = "/add-product")
    public ResponseEntity<String> addProduct(@RequestParam("productName") String productName, @RequestParam("brandName") String brandName
            , @RequestParam("categoryName") String categoryName, @RequestParam("price") Double price,
                                             @RequestParam("file") MultipartFile image);

    @PostMapping(value = "/test")
    public ResponseEntity<String> testSaveImg(@RequestParam("file") MultipartFile file);

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable(value = "id") Integer productId, @RequestBody Product product);

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(value = "id") Integer productId);

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Integer productId) ;

    @GetMapping(value = "/search/{keyword}")
    public ResponseEntity<List<Product>> findProductList(@RequestParam("page") Integer page, @PathVariable("keyword") String keyword,
                                                         @RequestParam("brand") List<String> brandList
    ) ;

    @GetMapping(value = "/category/{categoryName}")
    public ResponseEntity<List<Product>> findProductListByCategory(@RequestParam("page") Integer page,
                                                                   @PathVariable("categoryName") String categoryName
    );

    @GetMapping(value = "/brand/{brandName}")
    public ResponseEntity<List<Product>> findProductListByBrand(@RequestParam("page") Integer page,
                                                                @PathVariable("brandName") String brandName
    );
//    end CRUD


    @GetMapping(value = "/test-callback")
    public ResponseEntity<String> testCall();

//    review
@PostMapping(value = "/review/add-review")
public ResponseEntity<String> addReview(@RequestBody Review review) ;

    @PutMapping(value = "/review/{id}")
    public ResponseEntity<String> updateReview(@PathVariable("id") Integer reviewId, @RequestBody Review review);

    @DeleteMapping(value = "/review/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Integer reviewId) ;

    @GetMapping(value = "/get-reviews/{productId}")
    public ResponseEntity<Page<Review>> getReviews(@PathVariable("productId") Integer productId,
                                                   @RequestParam("page") Integer page) ;


}
