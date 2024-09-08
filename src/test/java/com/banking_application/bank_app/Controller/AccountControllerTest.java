package com.banking_application.bank_app.Controller;

import com.banking_application.bank_app.Dto.AccountDto2;
import com.banking_application.bank_app.Dto.TransportFundDto;
import com.banking_application.bank_app.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;


     @MockBean
     private AccountService accountService;


      private  ObjectMapper objectMapper;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void addAccount() throws  Exception{
        AccountDto2 accountDto=new AccountDto2(1L,"TEST",500);

        Mockito.when(accountService.createAccount(any(AccountDto2.class))).thenReturn(accountDto);
       mockMvc.perform(post("/api/accounts/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(accountDto))).
               andExpect(status().isCreated()).
               andExpect(jsonPath("$.balance").value(500));

       Mockito.verify(accountService,Mockito.times(1)).createAccount(any(AccountDto2.class));
    }

    @Test
    void findById()throws  Exception  {
        AccountDto2 accountDto=new AccountDto2(1L,"TEST",500);
        Mockito.when(accountService.getAccountById(accountDto.getId())).thenReturn(accountDto);
        mockMvc.perform(get("/api/accounts/{id}",accountDto.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(accountDto))).
                andExpect(status().isOk());

    }

    @Test
    void deposit() throws  Exception {
         double amount=500;
        AccountDto2 accountDto=new AccountDto2(1L,"TEST",500);
        accountDto.setBalance(500+amount);

        Map<String, Double> requestBody = new HashMap<>();
        requestBody.put("amount", amount);

        Mockito.when(accountService.Deposit(accountDto.getId(),amount)).thenReturn(accountDto);
        mockMvc.perform(put("/api/accounts/{id}/deposit",accountDto.getId()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(requestBody))).andExpect(status().isOk()).andExpect(
                        (jsonPath("$.balance").value(1000.0)));





    }

    @Test
    void withdraw() throws  Exception{

        double amount=300;
        AccountDto2 accountDto=new AccountDto2(1L,"TEST",500);
        accountDto.setBalance(500-amount);

        Map<String, Double> requestBody = new HashMap<>();
        requestBody.put("amount", amount);

        Mockito.when(accountService.WithDraw(accountDto.getId(),amount)).thenReturn(accountDto);
        mockMvc.perform(put("/api/accounts/{id}/withdraw",accountDto.getId()).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(requestBody))).andExpect(status().isOk()).andExpect(
                (jsonPath("$.balance").value(200.0)));

    }

    @Test
    void findAll() throws  Exception{
        List<AccountDto2> accountDtoList=new ArrayList<>();
        accountDtoList.add(new AccountDto2(1L,"TEST",500));
        Mockito.when(accountService.FindAll()).thenReturn(accountDtoList);
        mockMvc.perform(get("/api/accounts/all").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(accountDtoList))).andExpect(status().isOk());

        Mockito.verify(accountService,Mockito.times(1)).FindAll();

    }

    @Test
    void deletebyId() throws Exception{
        AccountDto2 accountDto=new AccountDto2(1L,"TEST",500);

       doNothing().when(accountService).DeleteById(accountDto.getId());
       mockMvc.perform(delete("/api/accounts/{id}",accountDto.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(accountDto))).andExpect(status().isOk());
    }

    @Test
    void transferFund()throws  Exception {
        TransportFundDto transportFundDto=new TransportFundDto(1L,1L,500);

        doNothing().when(accountService).transferFunds(any(TransportFundDto.class));
        mockMvc.perform(post("/api/accounts/transfer").contentType(MediaType.APPLICATION_JSON).content(objectMapper.
                writeValueAsString(transportFundDto))).andExpect(status().isOk());



    }
}