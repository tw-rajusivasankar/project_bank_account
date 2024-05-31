package com.example.project_bank_account.data.domain;

import com.example.project_bank_account.enums.TransactionType;
import com.example.project_bank_account.util.AppConstants;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = AppConstants.DbConstants.TABLE_BANK_ACCOUNT ,schema = AppConstants.DbConstants.SCHEMA_NAME)
@Data
public class BankAccount extends AuditEntity {

    private boolean isActive;
    private String userName;
    private String userMobileNumber;
    private String userPassword;
    private int age;
    private String emailAddress;
    private String address;
    private String pincode;
    private String type;

    @Column(unique = true)
    private String bankAccountNumber;

    private BigDecimal amount;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<BankAccountTransaction> transactions;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<BankAccountLoan> loans;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<CreditCard> creditCards;

    public void updateBalance(BigDecimal amount, TransactionType transactionType) {
        if (transactionType == TransactionType.DEPOSIT || transactionType == TransactionType.TRANSFER) {
            this.amount = this.amount.add(amount);
        } else if (transactionType == TransactionType.WITHDRAWAL) {
            this.amount = this.amount.subtract(amount);
        }
    }
}