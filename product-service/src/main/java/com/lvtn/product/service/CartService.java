package com.lvtn.product.service;


import com.lvtn.exception.BaseException;
import com.lvtn.product.entity.Cart;
import com.lvtn.product.entity.PurchaseRequest;
import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.CartRepository;
import com.lvtn.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createCart(Integer userId) {
        Cart cart = Cart.builder()
                .id(userId)
                .purchaseRequests(new ArrayList<>())
                .build();
        cartRepository.save(cart);
    }

    public void addToCart(int id, int productId, int quantity) {

    }

    public void updateCart(int id, int productId, int quantity) {
        PurchaseRequest i = null;
        Cart cart = cartRepository.getReferenceById(id);
        for (PurchaseRequest purchaseRequest : cart.getPurchaseRequests()) {
            if (purchaseRequest.getProduct().getId() == productId) {
                i = purchaseRequest;
            }
        }
        if (i != null) {
            i.setQuantity(i.getQuantity() + quantity);
            if (i.getQuantity() <= 0) {
                removeItem(id, productId);
            }
        }
    }

    public void removeItem(int id, int productId) {
        PurchaseRequest i = null;
        Cart cart = cartRepository.getReferenceById(id);
        for (PurchaseRequest purchaseRequest : cart.getPurchaseRequests()) {
            if (purchaseRequest.getProduct().getId() == productId) {
                i = purchaseRequest;
            }
        }
        if (i != null) {
            cart.getPurchaseRequests().remove(i);
        }
    }

    public Integer purchaseProducts(Cart cart){
        for(PurchaseRequest purchaseRequest : cart.getPurchaseRequests()){
            if(purchaseRequest.getQuantity() > purchaseRequest.getProduct().getAvailableQuantity()){
                throw new BaseException(200, "Insufficient stock  quantity for product " + purchaseRequest.getProduct().getId());
            }
            var newAvailableQuantity = purchaseRequest.getProduct().getAvailableQuantity() - purchaseRequest.getQuantity();
            Product product = productRepository.findById(purchaseRequest.getProduct().getId()).orElseThrow(() -> new BaseException(404, "product not found"));
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
        }
        return 1;

    }

}


