package com.ecommerce.shop_service.services;


import com.ecommerce.shop_service.entities.CartItem;
import com.ecommerce.shop_service.feigns.InventoryClient;
import com.ecommerce.shop_service.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public CartItem addItemToCart(Long userId, Long productId, Integer quantity) {
        CartItem item = new CartItem(productId, userId, quantity);
        return cartRepository.save(item);
    }

    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void removeItem(Long userId, Long itemId) {
        CartItem item = cartRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        if (!item.getUserId().equals(userId)) {
            throw new RuntimeException("Cannot remove another user's item");
        }
        cartRepository.deleteById(itemId);
    }

    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}
