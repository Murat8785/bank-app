package com.banking_application.bank_app.Mapper;

import com.banking_application.bank_app.Dto.AccountDto2;
import com.banking_application.bank_app.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMapperTest {


    private AccountMapper accountMapper;


    @BeforeEach
    void setUp() {
        accountMapper=new AccountMapper();
    }

    @Test
    void mapToAccount() {
        AccountDto2 accountDto=new AccountDto2(1L,"test",500);

        Account account=accountMapper.mapToAccount(accountDto);

        assertEquals(account.getId(),accountDto.getId());
        assertEquals(account.getBalance(),accountDto.getBalance());
        assertEquals(account.getAccountHolderName(),accountDto.getAccountHolderName());


    }

    @Test
    void mapToAccountDto() {
         Account account=new Account(1L,"test",500);

         AccountDto2 accountDto=accountMapper.mapToAccountDto(account);

        assertEquals(account.getId(),accountDto.getId());
        assertEquals(account.getBalance(),accountDto.getBalance());
        assertEquals(account.getAccountHolderName(),accountDto.getAccountHolderName());



    }
}