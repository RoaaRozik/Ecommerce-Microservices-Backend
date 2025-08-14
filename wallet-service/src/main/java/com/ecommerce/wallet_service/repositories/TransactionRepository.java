package com.ecommerce.wallet_service.repositories;

import com.ecommerce.wallet_service.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction,Long> {
   List<Transaction> findTransactionsByUserId(Long userId);
}
