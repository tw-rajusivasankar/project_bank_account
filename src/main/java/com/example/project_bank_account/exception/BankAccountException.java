package com.example.project_bank_account.exception;

import lombok.Getter;

@Getter
public class BankAccountException extends RuntimeException
{
    private final BankAccountExceptionCodes bankAccountExceptionCodes;
    private final String[] placeHolders;

    public BankAccountException(BankAccountExceptionCodes bankAccountExceptionCodes, String... placeHolders) {
        this.bankAccountExceptionCodes = bankAccountExceptionCodes;
        this.placeHolders = placeHolders;
    }
}