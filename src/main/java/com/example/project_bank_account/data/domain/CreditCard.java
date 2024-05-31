package com.example.project_bank_account.data.domain;

import com.example.project_bank_account.enums.CardType;
import com.example.project_bank_account.util.AppConstants;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = AppConstants.DbConstants.TABLE_BANK_CREDIT_CARD, schema = AppConstants.DbConstants.SCHEMA_NAME)
@Data
public class CreditCard extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_number", referencedColumnName = "bankAccountNumber", nullable = false)
    private BankAccount bankAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    @Column(name = "available_credit")
    private BigDecimal availableCredit;
}