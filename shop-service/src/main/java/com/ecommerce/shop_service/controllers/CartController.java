package com.ecommerce.shop_service.controllers;

import com.ecommerce.shop_service.Authentication.service.JwtUtil;
import com.ecommerce.shop_service.entities.CartItem;
import com.ecommerce.shop_service.services.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        return jwtUtil.extractUserId(token);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItem(HttpServletRequest request,
                                            @RequestParam Long productId,
                                            @RequestParam Integer quantity) {
        Long userId = getUserIdFromRequest(request);
        return ResponseEntity.ok(cartService.addItemToCart(userId, productId, quantity));
    }

    @GetMapping("/me")
    public ResponseEntity<List<CartItem>> getMyCart(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return ResponseEntity.ok(cartService.getCartItemsByUserId(userId));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<String> removeItem(HttpServletRequest request, @PathVariable Long itemId) {
        Long userId = getUserIdFromRequest(request);
        cartService.removeItem(userId, itemId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }
}
