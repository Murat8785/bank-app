package com.banking_application.bank_app.service;

import com.banking_application.bank_app.Dto.AccountDto2;
import com.banking_application.bank_app.Dto.TransportFundDto;

import java.util.List;


public interface AccountService  {

  AccountDto2 createAccount(AccountDto2 accountDto);

  AccountDto2 getAccountById(Long id);

  AccountDto2 Deposit(Long id, double amount);

  AccountDto2 WithDraw(Long id, double amount);

   List<AccountDto2> FindAll();

   void DeleteById(Long id);

   void transferFunds(TransportFundDto transportFundDto);
}

