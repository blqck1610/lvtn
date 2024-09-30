package com.lvtn.product.service;


import com.lvtn.utils.dto.product.ProductDto;
import com.lvtn.utils.dto.product.PurchaseRequest;
import com.lvtn.utils.dto.product.PurchaseResponse;
import com.lvtn.utils.dto.product.ProductRequest;
import com.lvtn.product.entity.Brand;
import com.lvtn.product.entity.Category;
import com.lvtn.product.entity.Gender;
import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.BrandRepository;
import com.lvtn.product.repository.CartRepository;
import com.lvtn.product.repository.CategoryRepository;
import com.lvtn.product.repository.ProductRepository;
import com.lvtn.utils.exception.BaseException;
import jakarta.servlet.ServletContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ServletContext context;
    private final ProductMapper productMapper;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final CartRepository cartRepository;

    private  Integer pageSize = 8;

//save product
    @Transactional
    public Integer saveProduct(Product product) {
        product = productRepository.saveAndFlush(product);
        return product.getId();
    }
// update product
    public String updateProduct(Integer productId, Product product) {
//        todo: implement update

        return null;
    }
// find product by id
    public ProductDto findById(Integer productId) {
        return productRepository.findById(productId).map(productMapper::toProductDto).orElse(null);
    }
// delete product
    public Integer deleteProduct(Integer productId) {
//        todo: implement delete
        return null;
    }
// save img or upload to cloud storage
    public String saveImg(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(this.getFolderUpload(), fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath();
    }

    public File getFolderUpload() {
        File folderUpload = new File(System.getProperty("user.dir") + "/image/product");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
//find products
    public Page<Product> findProducts(int page, String keyword, List<Brand> brands, List<Category> categories, Sort sort) {
        return productRepository.findProducts(getPageable(page, sort), keyword, brands, categories);

    }

    private Pageable getPageable(int page, Sort sort) {
        int pageSize = 8;
        return PageRequest.of(page, pageSize, sort);
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

// purchase products
    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requests) {
        var productIds = requests.stream().map(PurchaseRequest::getProductId).toList();
        List<Product> storedProducts = productRepository.findAllByIdInOrderById(productIds);
        List<PurchaseRequest> storedRequests = requests.stream().sorted(Comparator.comparing(PurchaseRequest::getProductId)).toList();

        if (productIds.size() != storedProducts.size()) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "One or more products does not exist");
        }
        List<PurchaseResponse> purchasedProducts = new ArrayList<PurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequests.get(i);
            if (product.getAvailableQuantity() < productRequest.getQuantity()) {
                throw new BaseException(HttpStatus.BAD_REQUEST, "Insufficient stock quantity  for product with id: " + product.getId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.getQuantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.saveAndFlush(product);
            purchasedProducts.add(productMapper.toPurchaseResponse(product, productRequest.getQuantity()));
        }
        return purchasedProducts;
    }
// save products without img
    public Integer saveProducts(List<ProductRequest> productsRaw) {

        for (ProductRequest productRequest : productsRaw) {
            Gender gender;
            try {
                gender = Gender.valueOf(productRequest.getGender().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BaseException(HttpStatus.BAD_REQUEST, "invalid gender");
            }
            Product product = Product.builder()
                    .productName(productRequest.getProductName())
                    .price(productRequest.getPrice())
                    .gender(gender)
                    .brand(brandRepository.findByName(productRequest.getBrand()).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "brand not found")))
                    .category(categoryRepository.findByName(productRequest.getCategory()).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "category not found")))
                    .description(productRequest.getDescription())
                    .availableQuantity(productRequest.getAvailableQuantity())
                    .imageUrl(productRequest.getImageUrl())

                    .build();
            productRepository.save(product);

        }

        return 1;


    }
// find new arrival produsts
    public Page<ProductDto> getNewArrivals(Integer page) {
        Pageable pageable = getPageable(page);

        return productRepository.findAll(pageable).map(productMapper::toProductDto);
    }
    private Pageable getPageable(Integer page){
        return PageRequest.of(page, pageSize);
    }
    private Pageable getPageable(Integer page, Sort sort){
        return PageRequest.of(page, pageSize, sort);
    }

// find product by gender
    public Page<ProductDto> findProductsByGender(Integer page, String genderRq) {
        Gender gender;
        try {
            gender = Gender.valueOf(genderRq.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "invalid gender");
        }
        return productRepository.findByGender(getPageable(page), gender).map(productMapper::toProductDto);

    }


    public List<ProductDto> search(String query) {
        return productRepository.search(query).stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }
}
