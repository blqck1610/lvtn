package com.lvtn.product.service.imp;

import com.lvtn.product.dto.request.AddItemRequest;
import com.lvtn.product.dto.request.UpdateItemRequest;
import com.lvtn.product.dto.response.CartResponse;
import com.lvtn.product.entity.Cart;
import com.lvtn.product.entity.Item;
import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.CartRepository;
import com.lvtn.product.repository.ItemRepository;
import com.lvtn.product.repository.ProductRepository;
import com.lvtn.product.service.CartService;
import com.lvtn.product.service.Mapper;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

/**
 * CartServiceImp
 * Version 1.0
 * Date: 11/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/11/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final Mapper mapper;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public CartResponse getCartResponse() {
        Cart cart = getCart();
        return mapper.from(cart);
    }

    @Transactional
    public Cart getCart() {
        String username = getUsername();
        Cart cart = cartRepository.getByUsername(username);
        if (ObjectUtils.isEmpty(cart)) {
            cart = Cart.builder()
                    .username(username)
                    .build();
            cart.setIsDelete(false);
            cart = cartRepository.saveAndFlush(cart);
        }
        return cart;
    }

    @Override
    @Transactional
    public CartResponse addItem(AddItemRequest request) {
        Product product = productRepository.getReferenceById(UUID.fromString(request.getProductId()));
        Cart cart = getCart();
        Item item = Item.builder()
                .cart(cart)
                .product(product)
                .quantity(request.getQuantity())
                .build();
        itemRepository.save(item);
        return mapper.from(cart);
    }

    @Transactional
    @Override
    public void deleteItem(String id) {
        Item item = itemRepository.getReferenceById(UUID.fromString(id));
        itemRepository.delete(item);
    }

    @Override
    @Transactional
    public CartResponse updateItem(UpdateItemRequest request){
        Item item = itemRepository.findById(UUID.fromString(request.getId())).orElseThrow(() -> new BaseException(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage()));
        item.setQuantity(request.getQuantity());
        itemRepository.save(item);
        return mapper.from(item.getCart());
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new BaseException(ErrorCode.UNAUTHENTICATED.getCode(), ErrorCode.UNAUTHENTICATED.getMessage());
        }
        return authentication.getName();
    }
}
