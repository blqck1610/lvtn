package com.lvtn.product.service;


import com.lvtn.utils.dto.product.CartResponse;
import com.lvtn.product.entity.Cart;
import com.lvtn.product.entity.Item;
import com.lvtn.product.repository.CartRepository;
import com.lvtn.product.repository.ItemRepository;
import com.lvtn.product.repository.ProductRepository;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    @Transactional(rollbackFor = {SQLException.class})
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
        if(isContainProduct(productId, cart)){
            updateCart(username, productId, 1);
        }
        else {
            Item item = itemRepository.saveAndFlush(Item.builder()
                    .product(productRepository.findById(productId).orElseThrow(
                            () -> new BaseException(HttpStatus.BAD_REQUEST, "product-not-found")))
                    .quantity(quantity)
                    .cart(cart)
                    .build());
            cart.getItems().add(item);
            cartRepository.saveAndFlush(cart);
        }
        return "add to cart successfully";

    }
    private Boolean isContainProduct(Integer productId, Cart cart) {
        for(Item item : cart.getItems()) {
            if(item.getProduct().getId().equals(productId)){
                return true;
            }
        }
        return false;
    }

    public String updateCart(String username, Integer productId, Integer quantity) {
        Item i = null;
        Cart cart = cartRepository.findByUsername(username).
                orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "cart-not-found"));
        for (Item item : cart.getItems()) {
            if (item.getProduct().getId() == productId) {
                i = item;
            }
        }
        if(i == null){
            addToCart(username, productId, 1);
        }
        else  {
            i.setQuantity(i.getQuantity() + quantity);
            itemRepository.save(i);
            if (i.getQuantity() <= 0) {
                removeItem(username, productId);
            }
        }

        cartRepository.save(cart);
        return "update successfully";
    }


    public String removeItem(String username, int productId) {
        Item i = null;
        Cart cart = cartRepository.findByUsername(username).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "Couldn't find cart with username: " + username));
        for (Item item : cart.getItems()) {
            if (item.getProduct().getId() == productId) {
                i = item;
            }
        }
        if (i != null) {
            cart.getItems().remove(i);
        }
        cartRepository.save(cart);
        return "deleted item successfully";
    }


    public CartResponse getCart(String username) {

        return new ProductMapper().toCartResponse(
                cartRepository.findByUsername(username).
                        orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "cart-not-found")));
    }


    public String clearCart(String username) {
        Cart cart = cartRepository.findByUsername(username).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "cart not found for username:" + username));
        cart.getItems().clear();
        cartRepository.save(cart);
        return "clear successfully";


    }
}


