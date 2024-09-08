package com.banking_application.bank_app.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto2 {

    private Long id;

     private String accountHolderName;

    private double balance;

}
