package com.example.project_bank_account.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BankAccountBalanceResponse {
    private String bankAccountNumber;
    private BigDecimal money;
    private LocalDateTime checkedTime;
}