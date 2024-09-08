package com.banking_application.bank_app.Dto;

public record TransportFundDto(
        Long fromAccountId,
        Long toAccountId,
        double amount
) {
}
