package com.banking_application.bank_app.service;

import com.banking_application.bank_app.Dto.AccountDto2;
import com.banking_application.bank_app.Dto.TransportFundDto;
import com.banking_application.bank_app.entity.Account;
import com.banking_application.bank_app.entity.Transaction;
import com.banking_application.bank_app.repository.AccountRepository;
import com.banking_application.bank_app.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImpTest {


    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImp accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccountTest() {
        AccountDto2 accountDto ;
        accountDto = new AccountDto2(1L,"enes",500);
        Account account = new Account();

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto2 createdAccount = accountService.createAccount(accountDto);

        assertNotNull(createdAccount);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void getAccountByIdTest() {
        Long accountId = 1L;
        Account account = new Account();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountDto2 accountDto = accountService.getAccountById(accountId);

        assertNotNull(accountDto);
        verify(accountRepository, times(1)).findById(accountId);
    }
    @Test
    void depositTest() {
        Long accountId = 1L;
        double depositAmount = 100.0;
        Account account = new Account();
        account.setBalance(200.0);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto2 accountDto = accountService.Deposit(accountId, depositAmount);

        assertEquals(300.0, account.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void withdrawTest() {
        Long accountId = 1L;
        double withdrawAmount = 50.0;
        Account account = new Account();
        account.setBalance(100.0);
      when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto2 accountDto = accountService.WithDraw(accountId, withdrawAmount);

        assertEquals(50.0, account.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void findAllTest() {
        Account account1 = new Account();
        Account account2 = new Account();

        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));

        assertNotNull(accountService.FindAll());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdTest() {
        Long accountId = 1L;
        Account account = new Account();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountService.DeleteById(accountId);
        //account silindi mi doğrula  diyoruz burada
        verify(accountRepository).deleteById(accountId);
    }

    @Test
    void transferFundsTest() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        double amount = 50.0;
        Account fromAccount = new Account();
        fromAccount.setBalance(100.0);
        Account toAccount = new Account();
        toAccount.setBalance(50.0);

        when(accountRepository.findById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(toAccountId)).thenReturn(Optional.of(toAccount));

        TransportFundDto transportFundDto = new TransportFundDto(fromAccountId, toAccountId, amount);
        accountService.transferFunds(transportFundDto);

        assertEquals(50.0, fromAccount.getBalance());
        assertEquals(100.0, toAccount.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
    }

    @Test
    void insufficientFundsTest() {
        Long accountId = 1L;
        double withdrawAmount = 200.0;
        Account account = new Account();
        account.setBalance(100.0);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(RuntimeException.class, () -> {
            accountService.WithDraw(accountId, withdrawAmount);
        });

        verify(accountRepository, times(1)).findById(accountId);
    }
}