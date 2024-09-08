package com.banking_application.bank_app.service;


import com.banking_application.bank_app.Dto.AccountDto2;
import com.banking_application.bank_app.Dto.TransportFundDto;
import com.banking_application.bank_app.Mapper.AccountMapper;
import com.banking_application.bank_app.entity.Account;
import com.banking_application.bank_app.entity.Transaction;
import com.banking_application.bank_app.exception.AccountException;
import com.banking_application.bank_app.repository.AccountRepository;
import com.banking_application.bank_app.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImp implements AccountService {



   private AccountRepository accountRepository;


    private TransactionRepository transactionRepository;

    private static final String TRANSACTION_TYPE_DEPOSIT="Deposit";
    private static final String TRANSACTION_TYPE_WITHDRAW="WithDraw";
    private static final String TRANSACTION_TYPE_TRANSFER="Transfer";
    public AccountServiceImp(AccountRepository accountRepository,TransactionRepository transactionRepository) {
        this.transactionRepository=transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto2 createAccount(AccountDto2 accountDto) {
        Account account= AccountMapper.mapToAccount((accountDto));
       Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto2 getAccountById(Long id) {

    Account account= accountRepository.findById(id).orElseThrow(()-> new AccountException("Account Bulunamadı"));


        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto2 Deposit(Long id, double amount) {
        Account account= accountRepository.findById(id).orElseThrow(()-> new AccountException("Account Bulunamadı"));

       double total=  account.getBalance()+amount;

       account.setBalance(total);



     Account savedAccount=  accountRepository.save(account);
        Transaction transaction=new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setTimeStamp(LocalDateTime.now());

        transactionRepository.save(transaction);
        return  AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto2 WithDraw(Long id, double amount) {
        Account account= accountRepository.findById(id).orElseThrow(()-> new AccountException("Account Bulunamadı"));

       if(account.getBalance() < amount){
        throw new RuntimeException("bakiyeniz yetersiz");
       }else{

        double total=  account.getBalance()-amount;

        account.setBalance(total);

        Account savedAccount=  accountRepository.save(account);



           Transaction transaction=new Transaction();
           transaction.setAccountId(id);
           transaction.setAmount(amount);
           transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
           transaction.setTimeStamp(LocalDateTime.now());

           transactionRepository.save(transaction);
        return  AccountMapper.mapToAccountDto(savedAccount);
    }
    }

    @Override
    public List<AccountDto2> FindAll() {

       List<Account> accounts=accountRepository.findAll();

       return accounts.stream().map(AccountMapper::mapToAccountDto).collect(Collectors.toList());
    }

    @Override
    public void  DeleteById(Long id) {
        Account account= accountRepository.findById(id).orElseThrow(()-> new AccountException("Account Bulunamadı"));

        accountRepository.deleteById(id);

    }

    @Override
    public void transferFunds(TransportFundDto transportFundDto) {
   Account fromAccount= accountRepository.findById(transportFundDto.fromAccountId()).orElseThrow(
                ()-> new AccountException("Account bulunamadı"));


        Account toAccount= accountRepository.findById(transportFundDto.toAccountId()).orElseThrow(
                ()-> new AccountException("Account bulunamadı"));

        fromAccount.setBalance(fromAccount.getBalance()- transportFundDto.amount());
        toAccount.setBalance(toAccount.getBalance()+ transportFundDto.amount());

        Transaction transaction=new Transaction();
        transaction.setAccountId(transportFundDto.toAccountId());
        transaction.setAmount(transportFundDto.amount());
        transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER);
        transaction.setTimeStamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        accountRepository.save(fromAccount);

        accountRepository.save(toAccount);


    }


}
