package com.example.project_bank_account.model.response;

import com.example.project_bank_account.enums.BankAccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BankAccountResponse {

    private UUID userId;
    private String userName;
    private String userMobileNumber;
    private String userPassword;
    private boolean isActive;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
    private BankAccountType bankAccountType;
    private String bankAccountNumber;
    private BigDecimal amount;

}