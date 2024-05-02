package com.lvtn.product.service;


import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.ProductRepository;
import jakarta.servlet.ServletContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
    public Product saveProduct(Product product) {
        product = productRepository.saveAndFlush(product);
        return product;
    }

    public String updateProduct(Integer productId, Product product) {
//        todo: implement update

        return null;
    }

    public Product getProduct(Integer productId) {
        Product product = productRepository.getReferenceById(productId);
        return product;
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

    public Page<Product> findProducts(int page, String keyword, List<String> brands, List<String> categories, Sort sort){
        return productRepository.findProducts(getPageable(page, sort), keyword, brands, categories);

    }

    private Pageable getPageable(int page, Sort sort) {
        int pageSize = 8;
        return PageRequest.of(page, pageSize, sort);
    }
}
