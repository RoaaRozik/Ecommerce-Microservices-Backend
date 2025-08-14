package com.ecommerce.wallet_service.services;

import com.ecommerce.wallet_service.entities.Transaction;
import com.ecommerce.wallet_service.entities.TransactionType;
import com.ecommerce.wallet_service.entities.User;
import com.ecommerce.wallet_service.repositories.TransactionRepository;
import com.ecommerce.wallet_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction createTransaction(Long Id, BigDecimal amount, String type) {
        User user = userRepository.findUserById(Id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        TransactionType transType;
        try {
            transType = TransactionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid transaction type,DEPOSIT or WITHDRAW only");
        }


        Transaction transaction = new Transaction(amount, transType, user);
        transactionRepository.save(transaction);
        return transaction;
    }
    public List<Transaction> getTransactionsByUserId(Long Id){
        return transactionRepository.findTransactionsByUserId(Id);
    }

}
