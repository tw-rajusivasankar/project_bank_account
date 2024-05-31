package com.example.project_bank_account.model.response;

import com.example.project_bank_account.enums.CardType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CreditCardDetailsResponse {

    private UUID id;
    private String bankAccountNumber;
    private CardType cardType;
    private BigDecimal creditLimit;
    private BigDecimal availableCredit;
    private LocalDateTime createdDate;
    private LocalDateTime expiryDate;
    private String status;
}