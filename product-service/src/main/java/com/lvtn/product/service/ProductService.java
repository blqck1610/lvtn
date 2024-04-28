package com.lvtn.product.service;


import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.ProductRepository;
import jakarta.servlet.ServletContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ServletContext context;

    public String addProduct(Product product, MultipartFile multipartFile) {
//        productRepository.save(product);

        String fileName = multipartFile.getOriginalFilename();
        File file = new File(this.getFolderUpload(), fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "success";
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


    public File getFolderUpload() {

        File folderUpload = new File(System.getProperty("user.dir") + "/image/product");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

}
