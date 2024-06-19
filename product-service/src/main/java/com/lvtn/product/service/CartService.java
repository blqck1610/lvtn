package com.lvtn.product.service;


import com.lvtn.exception.BaseException;
import com.lvtn.product.entity.Cart;
import com.lvtn.product.entity.Item;
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
                .items(new ArrayList<>())
                .build();
        cartRepository.save(cart);
    }

    public void addToCart(int id, int productId, int quantity) {

    }

    public void updateCart(int id, int productId, int quantity) {
        Item i = null;
        Cart cart = cartRepository.getReferenceById(id);
        for (Item item : cart.getItems()) {
            if (item.getProduct().getId() == productId) {
                i = item;
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
        Item i = null;
        Cart cart = cartRepository.getReferenceById(id);
        for (Item item : cart.getItems()) {
            if (item.getProduct().getId() == productId) {
                i = item;
            }
        }
        if (i != null) {
            cart.getItems().remove(i);
        }
    }

    public Integer purchaseProducts(Cart cart){
        for(Item item : cart.getItems()){
            if(item.getQuantity() > item.getProduct().getAvailableQuantity()){
                throw new BaseException(200, "Insufficient stock  quantity for product " + item.getProduct().getId());
            }
            var newAvailableQuantity = item.getProduct().getAvailableQuantity() - item.getQuantity();
            Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(() -> new BaseException(404, "product not found"));
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
        }
        return 1;

    }

}


