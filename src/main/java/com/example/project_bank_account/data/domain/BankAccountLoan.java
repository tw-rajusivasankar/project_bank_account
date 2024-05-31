package com.example.project_bank_account.data.domain;

import com.example.project_bank_account.enums.LoanType;
import com.example.project_bank_account.util.AppConstants;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = AppConstants.DbConstants.TABLE_BANK_LOAN ,schema = AppConstants.DbConstants.SCHEMA_NAME)
@Data
public class BankAccountLoan extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_number", referencedColumnName = "bankAccountNumber", nullable = false)
    private BankAccount bankAccount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Column(nullable = false)
    private BigDecimal loanAmount;

    private LocalDateTime approvedDate;

    @Column(nullable = false)
    private int loanTermInMonths;

    @Column(nullable = false)
    private BigDecimal monthlyInstallment;

    @Column(nullable = false)
    private String loanStatus;
}