package com.example.project_bank_account.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BankAccountWithdrawMoneyRequest {
    private String bankAccountNumber;
    private BigDecimal amount;
    private LocalDateTime withdrewTime;
}