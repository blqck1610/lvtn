package com.lvtn.product.service;


import com.lvtn.product.entity.Brand;
import com.lvtn.product.entity.Category;
import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.ProductRepository;
import com.lvtn.utils.exception.BaseException;
import jakarta.servlet.ServletContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ServletContext context;


    @Transactional
    public Integer saveProduct(Product product) {
        product = productRepository.saveAndFlush(product);
        return product.getId();
    }

    public String updateProduct(Integer productId, Product product) {
//        todo: implement update

        return null;
    }

    public Product findById(Integer productId) {
         return productRepository.findById(productId).orElseThrow(() -> new BaseException(404, "Product not found"));
    }

    public Product deleteProduct(Integer productId) {
//        todo: implement delete
        return null;
    }

    public String saveImg(MultipartFile multipartFile){
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

    public Page<Product> findProducts(int page, String keyword, List<Brand> brands, List<Category> categories, Sort sort){
        return productRepository.findProducts(getPageable(page, sort), keyword, brands, categories);

    }

    private Pageable getPageable(int page, Sort sort) {
        int pageSize = 8;
        return PageRequest.of(page, pageSize, sort);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
