package com.lvtn.product.controller;


import com.lvtn.product.entity.Product;
import com.lvtn.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Product saved successfully {}" , product);
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
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Integer productId){
        Product product = productService.getProduct(productId);
        return ResponseEntity.ok(product);
    }
    @GetMapping(value = "/search/{keyword}")
    public ResponseEntity<List<Product>> findProductList(@PathVariable("keyword") String keyword,
                                                         @RequestParam("brand") List<String> brandList
                                                         ){
//        todo: find product list by keyword, filter ....
        return null;
    }
//    end CRUD



    @GetMapping(value = "/test-callback")
    public ResponseEntity<String> testCall() {
        return ResponseEntity.ok("ok");
    }


}
