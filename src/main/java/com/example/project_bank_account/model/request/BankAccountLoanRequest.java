package com.example.project_bank_account.model.request;

import com.example.project_bank_account.enums.LoanType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountLoanRequest {

    @NotNull
    private String bankAccountNumber;

    @NotNull
    private LoanType loanType;

    @NotNull
    private BigDecimal loanAmount;

    @NotNull
    private int loanTermInMonths;
}