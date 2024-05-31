package com.example.project_bank_account.util;

import com.example.project_bank_account.data.domain.BankAccount;
import com.example.project_bank_account.model.response.BankAccountLoginResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BankAccountToLoginResponseConverter implements Converter<BankAccount, BankAccountLoginResponse> {

    @Override
    public BankAccountLoginResponse convert(BankAccount bankAccount) {
        return BankAccountLoginResponse.builder()
                .isLoggedIn(bankAccount.isActive())
                .isActive(bankAccount.isActive())
                .createdDateTime(bankAccount.getCreatedDateTime())
                .build();
    }
}