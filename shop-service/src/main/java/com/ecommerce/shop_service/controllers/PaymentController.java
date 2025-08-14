package com.ecommerce.shop_service.controllers;

import com.ecommerce.shop_service.Authentication.service.JwtUtil;
import com.ecommerce.shop_service.entities.Payment;
import com.ecommerce.shop_service.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(HttpServletRequest request,
                                                 @RequestParam Long orderId,
                                                 @RequestParam BigDecimal amount) {
        String token = request.getHeader("Authorization").substring(7); // remove "Bearer "
        Long userId = jwtUtil.extractUserId(token);

        Payment payment = paymentService.createPayment(orderId, userId, amount);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<Payment>> getMyPayments(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.extractUserId(token);

        List<Payment> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }
}
