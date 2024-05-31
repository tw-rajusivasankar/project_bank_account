package com.example.project_bank_account.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BankAccountLoginResponse {

    private boolean isLoggedIn;
    private boolean isActive;
    private LocalDateTime createdDateTime;

    @Data
    @Builder
    public static class BankAccountBalanceResponse {
        private double money;
        private LocalDateTime checkedTime;
    }
}