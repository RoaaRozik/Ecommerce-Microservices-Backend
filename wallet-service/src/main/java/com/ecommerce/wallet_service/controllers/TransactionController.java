package com.ecommerce.wallet_service.controllers;


import com.ecommerce.wallet_service.entities.Transaction;
import com.ecommerce.wallet_service.entities.User;
import com.ecommerce.wallet_service.repositories.UserRepository;
import com.ecommerce.wallet_service.services.TransactionService;
import com.ecommerce.wallet_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<List<Transaction>> getMyTransactions(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email); // make this method in UserService
        List<Transaction> transactions = transactionService.getTransactionsByUserId(user.getId());
        return ResponseEntity.ok(transactions);
    }


}
