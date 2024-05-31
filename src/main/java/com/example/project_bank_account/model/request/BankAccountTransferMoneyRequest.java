package com.example.project_bank_account.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BankAccountTransferMoneyRequest {
    private String sourceBankAccountNumber;
    private String destinationBankAccountNumber;
    private BigDecimal money;
    private LocalDateTime transferTime;
}