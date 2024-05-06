package com.lvtn.product.controller;


import com.lvtn.product.entity.Product;
import com.lvtn.product.entity.Review;
import com.lvtn.product.service.ProductService;
import com.lvtn.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    //CRUD API
    @PostMapping(value = "/add-product")
    public ResponseEntity<String> addProduct(@RequestParam("productName") String productName, @RequestParam("brandName") String brandName
            , @RequestParam("categoryName") String categoryName, @RequestParam("price") Double price,
                                             @RequestParam("file") MultipartFile image) {

        Product product = Product.builder()
                .productName(productName)
                .brandName(brandName)
                .categoryName(categoryName)
                .price(price)
                .imageUrl(productService.saveImg(image))
                .build();
        productService.saveProduct(product);
        log.info("Product saved successfully {}", product);
        return ResponseEntity.status(HttpStatus.CREATED).body("product saved successfully");
    }

    @PostMapping(value = "/test")
    public ResponseEntity<String> testSaveImg(@RequestParam("file") MultipartFile file) {
        log.info("save img {} ", productService.saveImg(file));

        return ResponseEntity.ok("ok");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable(value = "id") Integer productId, @RequestBody Product product) {
        // todo: update product

        return ResponseEntity.ok("ok");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(value = "id") Integer productId) {
        // todo: delete product

        return ResponseEntity.ok("ok");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Integer productId) {
        Product product = productService.getProduct(productId);

        if (product == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(product);
    }

    @GetMapping(value = "/search/{keyword}")
    public ResponseEntity<List<Product>> findProductList(@RequestParam("page") Integer page, @PathVariable("keyword") String keyword,
                                                         @RequestParam("brand") List<String> brandList
    ) {
//        todo: find product list by keyword, filter ....
        return null;
    }

    @GetMapping(value = "/category/{categoryName}")
    public ResponseEntity<List<Product>> findProductListByCategory(@RequestParam("page") Integer page,
                                                                   @PathVariable("categoryName") String categoryName
    ) {
//        todo: find product list by category ....
        page = page == null ? 0 : page;
        Page<Product> products = productService.findProducts(page, null, null, List.of(categoryName), Sort.by("productName"));
        return null;
    }

    @GetMapping(value = "/brand/{brandName}")
    public ResponseEntity<List<Product>> findProductListByBrand(@RequestParam("page") Integer page,
                                                                @PathVariable("brandName") String brandName
    ) {
//        todo: find product list by brand ....
        return null;
    }
//    end CRUD


    @GetMapping(value = "/test-callback")
    public ResponseEntity<String> testCall() {
        return ResponseEntity.ok("ok");
    }

    //    review
    @PostMapping(value = "/review/add-review")
    public ResponseEntity<String> addReview(@RequestBody Review review) {
        log.info("Adding review {}", review);
        reviewService.saveReview(review);
        return ResponseEntity.ok("Review added successfully");

    }

    @PutMapping(value = "/review/{id}")
    public ResponseEntity<String> updateReview(@PathVariable("id") Integer reviewId, @RequestBody Review review) {
        log.info("updating review {}", review);
        reviewService.updateReview(reviewId, review);
        return ResponseEntity.ok("Review update successfully");

    }

    @DeleteMapping(value = "/review/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Integer reviewId) {
        log.info("delete review {}", reviewId);
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("delete successfully");

    }

    @GetMapping(value = "/get-reviews/{productId}")
    public ResponseEntity<Page<Review>> getReviews(@PathVariable("productId") Integer productId,
                                                   @RequestParam(value = "page", required = false) Integer page) {
        page = page == null ? 0 : page;
        return ResponseEntity.ok(reviewService.getReviewsByProduct(page, productId));
    }


}
