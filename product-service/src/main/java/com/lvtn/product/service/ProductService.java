package com.lvtn.product.service;


import com.lvtn.clients.product.PurchaseRequest;
import com.lvtn.clients.product.PurchaseResponse;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ServletContext context;
    private final ProductMapper productMapper;


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
         return productRepository.findById(productId).orElse(null);
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



    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requests) {
        var productIds = requests.stream().map(PurchaseRequest::getProductId).toList();
        List<Product> storedProducts = productRepository.findAllByIdInOrderById(productIds);
        List<PurchaseRequest> storedRequests = requests.stream().sorted(Comparator.comparing(PurchaseRequest::getProductId)).toList();

        if(productIds.size() != storedProducts.size()) {
            throw new BaseException(200, "One or more products does not exist");
        }
        List<PurchaseResponse> purchasedProducts = new ArrayList<PurchaseResponse>();
        for(int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequests.get(i);
            if(product.getAvailableQuantity() < productRequest.getQuantity()){
                throw new BaseException(400, "Insufficient stock quantity  for product with id: " + product.getId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.getQuantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.saveAndFlush(product);
            purchasedProducts.add(productMapper.toPurchaseResponse(product, productRequest.getQuantity()));
        }


        return purchasedProducts;
    }
}
