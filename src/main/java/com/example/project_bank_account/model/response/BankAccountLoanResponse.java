package com.example.project_bank_account.model.response;

import com.example.project_bank_account.enums.LoanType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BankAccountLoanResponse {

    private UUID id;
    private String bankAccountNumber;
    private LoanType loanType;
    private BigDecimal loanAmount;
    private LocalDateTime approvedDate;
    private int loanTermInMonths;
    private BigDecimal monthlyInstallment;
    private String loanStatus;
}