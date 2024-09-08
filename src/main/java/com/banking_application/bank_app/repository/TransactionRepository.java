package com.banking_application.bank_app.repository;

import com.banking_application.bank_app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByAccountIdOrderByTimeStampDesc(Long accountId);
}
