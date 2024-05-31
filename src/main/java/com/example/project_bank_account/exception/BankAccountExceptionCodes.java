package com.example.project_bank_account.exception;

import lombok.Getter;

@Getter
public enum BankAccountExceptionCodes {
    INVALID_PARAMS_OR_REQUEST_BODY("INVALID_PARAMS_OR_REQUEST_BODY","Invalid request params or request body"),
    UNKNOWN("ES-UNKNOWN","Runtime exception"),
    BANK_ACCOUNT_INVALID_CREDENTIALS("LOGIN_CREDENTIALS_INVALID", "No user found with this details"),
    BANK_ACCOUNT_INVALID_ACCOUNT_NUMBER("ACCOUNT_NUMBER_WRONG", "Invalid bank account account number"),
    BANK_ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", "Invalid bank account"),
    BANK_ACCOUNT_LOAN_NOT_FOUND("LOAN_NOT_FOUND", "Loan not found"),
    BANK_ACCOUNT_INSUFFICIENT_BALANCE("IN-SUFFICIENT_BALANCE", "In sufficient balance"),
    BANK_ACCOUNT_LOGIN_CREDENTIALS_REQUIRED("LOGIN_CREDENTIALS", "Login credentials are required"),
    BANK_ACCOUNT_CREDIT_CARD_NOT_FOUND("CREDIT_CARD_NOT_FOUND", "Credit card not found with this id: %s"),
    BANK_ACCOUNT_MOBILE_EMPTY("MOBILE_NUMBER", "Customer mobile number is empty");

    public final String code;
    public final String description;

    BankAccountExceptionCodes(String code, String description) {
        this.code = code;
        this.description = description;
    }
}