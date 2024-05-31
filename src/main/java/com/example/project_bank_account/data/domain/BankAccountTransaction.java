package com.example.project_bank_account.data.domain;

import com.example.project_bank_account.enums.TransactionType;
import com.example.project_bank_account.util.AppConstants;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = AppConstants.DbConstants.TABLE_BANK_TRANSACTION, schema = AppConstants.DbConstants.SCHEMA_NAME)
@Data
public class BankAccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private BigDecimal amount;

    private LocalDateTime transactionTime;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // deposit, withdraw, transfer
    private String sourceAccountNumber; // for transfer
    private String destinationAccountNumber; // for transfer

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "uuid")
    private BankAccount bankAccount;

    public void updateBalance(BigDecimal amount, TransactionType transactionType) {
        if (transactionType == TransactionType.DEPOSIT || transactionType == TransactionType.TRANSFER) {
            this.amount = this.amount.add(amount); // Update this.amount
        } else if (transactionType == TransactionType.WITHDRAWAL) {
            this.amount = this.amount.subtract(amount); // Update this.amount
        }
    }
}