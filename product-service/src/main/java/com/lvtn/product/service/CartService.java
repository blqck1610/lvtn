package com.lvtn.product.service;


import com.lvtn.product.entity.Cart;
import com.lvtn.product.entity.Item;
import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.CartRepository;
import com.lvtn.product.repository.ItemRepository;
import com.lvtn.product.repository.ProductRepository;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Cart createCart(String username) {
        Cart cart = Cart.builder()
                .username(username)
                .items(new ArrayList<>())
                .build();
        return  cartRepository.saveAndFlush(cart);
    }

    public String addToCart(String username, int productId, int quantity) {
        Cart cart = cartRepository.findByUsername(username).orElse(null);
        if(cart == null){
            cart = createCart(username);
        }
        Item item = itemRepository.saveAndFlush(Item.builder()
                        .product(productRepository.findById(productId).orElseThrow(
                        () -> new BaseException(404, "Product not found, id: " + productId)))
                .quantity(quantity)
                        .cart(cart)
                .build());
        cart.getItems().add(item);
        return "add to cart successfully";

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



}


