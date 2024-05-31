package com.example.project_bank_account.service;

import com.example.project_bank_account.model.request.CreditCardApprovalRequest;
import com.example.project_bank_account.model.request.CreditCardCloseRequest;
import com.example.project_bank_account.model.request.CreditCardRequest;
import com.example.project_bank_account.model.response.CreditCardDetailsResponse;
import com.example.project_bank_account.model.response.CreditCardResponse;

import java.util.List;
import java.util.UUID;

public interface CreditCardService {

    CreditCardResponse applyForCreditCard(CreditCardRequest request);

    CreditCardResponse approveCreditCard(CreditCardApprovalRequest request);

    CreditCardResponse closeCreditCard(CreditCardCloseRequest request);

    CreditCardDetailsResponse getCreditCardDetails(UUID cardId);

    List<CreditCardDetailsResponse> getCreditCardsByBankAccountNumber(String bankAccountNumber);
}