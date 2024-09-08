package com.banking_application.bank_app.Mapper;

import com.banking_application.bank_app.Dto.AccountDto2;
import com.banking_application.bank_app.entity.Account;

public class AccountMapper {



   public static  Account mapToAccount(AccountDto2 accountDto){
       return new Account(
               accountDto.getId(),
               accountDto.getAccountHolderName(),
               accountDto.getBalance()
       );
   }

    public static AccountDto2 mapToAccountDto(Account account){
        return new AccountDto2(
               account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
    }





}
