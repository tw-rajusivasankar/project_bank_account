package com.example.project_bank_account.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class BankAccountLoanCloseRequest {

    @NotNull
    private UUID loanId;
}