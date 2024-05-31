package com.example.project_bank_account.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreditCardRequest {

    @NotBlank(message = "Bank account number is required")
    private String bankAccountNumber;
}