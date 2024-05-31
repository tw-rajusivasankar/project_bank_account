package com.example.project_bank_account.model.response;

import com.example.project_bank_account.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BankAccountMoneyResponse {
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private String bankAccountNumber;
    private BigDecimal currentBalance;
    private LocalDateTime transactionTime;
    private TransactionType transactionType;
    private String message;
}