package com.lvtn.product.controller;


import com.lvtn.clients.product.ProductDto;
import com.lvtn.clients.product.PurchaseRequest;
import com.lvtn.clients.product.PurchaseResponse;
import com.lvtn.product.dto.ProductRequest;
import com.lvtn.product.entity.*;
import com.lvtn.product.repository.BrandRepository;
import com.lvtn.product.repository.CategoryRepository;
import com.lvtn.product.service.ProductService;
import com.lvtn.product.service.ReviewService;
import com.lvtn.utils.exception.BaseException;
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
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    //CRUD API
//    add new product
    @PostMapping(value = "/add-product")
    public ResponseEntity<Integer> addProduct(@RequestParam("productName") String productName,
                                              @RequestParam("brandId")
                                              Integer brandId,
                                              @RequestParam("categoryId") Integer categoryId,
                                              @RequestParam("gender") String genderRaw,
                                              @RequestParam("price") Double price,
                                              @RequestParam("file") MultipartFile image) {
        Gender gender;
        try {
            gender = Gender.valueOf(genderRaw.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BaseException(400, "Invalid gender");
        }
        Product product = Product.builder()
                .productName(productName)
                .brand(brandRepository.getReferenceById(brandId))
                .category(categoryRepository.getReferenceById(categoryId))
                .price(price)
                .imageUrl(productService.saveImg(image)).build();
        log.info("Product saving successfully {}", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
    }

    @PostMapping(value = "/test")
    public ResponseEntity<String> testSaveImg(@RequestParam("file") MultipartFile file) {
        log.info("save img {} ", productService.saveImg(file));

        return ResponseEntity.ok("ok");
    }

    // update product
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable(value = "id") Integer productId, @RequestBody Product product) {
        // todo: update product

        return ResponseEntity.ok("ok");
    }

    // delete product
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(value = "id") Integer productId) {
        // todo: delete product

        return ResponseEntity.ok("ok");
    }

    //find product by id
    @GetMapping(value = "/product-details/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable(value = "id") Integer productId) {

        return ResponseEntity.ok(productService.findById(productId));
    }


    // get all products
    @GetMapping(value = "/find-all")
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    //find products by keyword
    @GetMapping(value = "/search/{keyword}")
    public ResponseEntity<List<Product>> findProductList(@RequestParam("page") Integer page, @PathVariable("keyword") String keyword, @RequestParam("brand") List<String> brandList) {
//        todo: find product list by keyword, filter ....
        return null;
    }

    // find product list by category
    @GetMapping(value = "/category/{categoryName}")
    public ResponseEntity<List<Product>> findProductListByCategory(@RequestParam("page") Integer page, @PathVariable("categoryName") String categoryName) {
//        todo: find product list by category ....
        page = page == null ? 0 : page;
        Category category = categoryRepository.findByName(categoryName).orElseThrow();
        Page<Product> products = productService.findProducts(page, null, null, List.of(category), Sort.by("productName"));
        return null;
    }

    //find products by brand
    @GetMapping(value = "/brand/{brandName}")
    public ResponseEntity<List<Product>> findProductListByBrand(@RequestParam("page") Integer page, @PathVariable("brandName") String brandName) {
//        todo: find product list by brand ....
        return null;
    }

    //    find new arrivals products
    @GetMapping(value = "/new-arrivals")
    public ResponseEntity<Page<ProductDto>> getNewArrivals(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        log.info("get new arrival products");
        return ResponseEntity.ok(productService.getNewArrivals(page));
    }

    // find product list gender
    @GetMapping(value = "/gender/{gender}")
    public ResponseEntity<Page<ProductDto>> getProductsByGender(
            @RequestParam(value = "page", defaultValue = "0") Integer page, @PathVariable("gender") String gender) {
        log.info("get products by gender " + gender);
        return ResponseEntity.ok(productService.findProductsByGender(page, gender));
    }


    //    purchase proucts
    @PostMapping(value = "/purchase")
    public ResponseEntity<List<PurchaseResponse>> purchaseProducts(@RequestBody List<PurchaseRequest> requests) {
        return ResponseEntity.ok(productService.purchaseProducts(requests));
    }


    @GetMapping(value = "/test-callback")
    public ResponseEntity<String> testCall() {
        return ResponseEntity.ok("ok");
    }

    //    review

    //brand
    @PostMapping(value = "/brand/add-brands")
    public ResponseEntity<String> addBrands(@RequestBody List<Brand> brands) {
        brandRepository.saveAll(brands);
        return ResponseEntity.ok("Brands added successfully");
    }

    //    category
    @PostMapping(value = "/category/add-categories")
    public ResponseEntity<String> addCategories(@RequestBody List<Category> categories) {
        categoryRepository.saveAll(categories);
        return ResponseEntity.ok("categories added successfully");
    }

    //    add product test
    @PostMapping(value = "/add-products")
    public ResponseEntity<Integer> addProducts(@RequestBody List<ProductRequest> products) {

        log.info("Product saving ");
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProducts(products));
    }
    @GetMapping(value = "/test1/{id}")
    public ResponseEntity<Integer> test(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

}
