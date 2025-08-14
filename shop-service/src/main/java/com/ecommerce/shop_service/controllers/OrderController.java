package com.ecommerce.shop_service.controllers;

import com.ecommerce.shop_service.Authentication.service.JwtUtil;
import com.ecommerce.shop_service.entities.Order;
import com.ecommerce.shop_service.entities.OrderItem;
import com.ecommerce.shop_service.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(HttpServletRequest request,
                                            @RequestBody List<OrderItem> items) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7); // remove "Bearer "
        Long userId = jwtUtil.extractUserId(token);

        Order order = orderService.placeOrder(userId, items);
        return ResponseEntity.ok(order);
    }



    @GetMapping("/user/me")
    public ResponseEntity<List<Order>> getMyOrders(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        List<Order> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
