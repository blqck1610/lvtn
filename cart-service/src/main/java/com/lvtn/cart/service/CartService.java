package com.lvtn.cart.service;


import com.lvtn.cart.entity.Cart;
import com.lvtn.cart.entity.Item;
import com.lvtn.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    @Transactional
    public void createCart(Integer userId) {
        Cart cart = Cart.builder()
                .id(userId)
                .items(new ArrayList<>())
                .build();
        cartRepository.save(cart);
    }
    public void addToCart(int id, int productId, int quantity){

    }
    public void updateCart(int id, int productId, int quantity){
        Item i = null;
        Cart cart = cartRepository.getReferenceById(id);
        for(Item item : cart.getItems()) {
            if(item.getProductId() == productId) {
                i = item;
            }
        }
        if(i != null) {
            i.setQuantity(i.getQuantity() + quantity);
            if(i.getQuantity() <= 0 ) {
                removeItem(id, productId);
            }
        }
    }
    public void removeItem(int id, int productId) {
        Item i = null;
        Cart cart = cartRepository.getReferenceById(id);
        for(Item item : cart.getItems()) {
            if(item.getProductId() == productId) {
                i = item;
            }
        }
        if(i != null) {
            cart.getItems().remove(i);
        }
    }

}
