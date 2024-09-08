package com.banking_application.bank_app.repository;

import com.banking_application.bank_app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
