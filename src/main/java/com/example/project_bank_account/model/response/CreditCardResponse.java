package com.example.project_bank_account.model.response;

import com.example.project_bank_account.enums.CardType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CreditCardResponse {

    private UUID id;
    private String bankAccountNumber;
    private CardType cardType;
    private BigDecimal creditLimit;
    private BigDecimal availableCredit;
}