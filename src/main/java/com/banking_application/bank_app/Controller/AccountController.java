package com.banking_application.bank_app.Controller;


import com.banking_application.bank_app.Dto.AccountDto2;
import com.banking_application.bank_app.Dto.TransportFundDto;
import com.banking_application.bank_app.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

   private  AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/add")
    public ResponseEntity<AccountDto2> AddAccount(@RequestBody AccountDto2 accountDto){

        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<AccountDto2> FindById(@PathVariable Long id){

        AccountDto2 accountDto1=accountService.getAccountById(id);

        return  ResponseEntity.ok(accountDto1);
    }


    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto2> Deposit(@PathVariable Long id,
                                               @RequestBody Map<String,Double> Request ){

      Double amount=  Request.get("amount");
        AccountDto2 accountDto=accountService.Deposit(id,amount);

        return ResponseEntity.ok(accountDto);

    }
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto2> Withdraw(@PathVariable Long id,
                                                @RequestBody Map<String,Double> Request ){

        Double amount=  Request.get("amount");
        AccountDto2 accountDto=accountService.WithDraw(id,amount);

        return ResponseEntity.ok(accountDto);

    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto2>> FindAll(){

         List<AccountDto2> all=accountService.FindAll();

         return ResponseEntity.ok(all);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> Delete(@PathVariable Long id){

      accountService.DeleteById(id);

      return ResponseEntity.ok("hesap silindi");
    }


    @PostMapping("/transfer")
    public ResponseEntity<String> transferFund(@RequestBody TransportFundDto transportFundDto){

       accountService.transferFunds(transportFundDto);

       return ResponseEntity.ok("Transfer Başarılı");

    }







}
