package com.example.project_bank_account.service.impl;

import com.example.project_bank_account.data.domain.CreditCard;
import com.example.project_bank_account.data.repo.BankAccountRepo;
import com.example.project_bank_account.data.repo.CreditCardRepo;
import com.example.project_bank_account.enums.CardType;
import com.example.project_bank_account.exception.BankAccountException;
import com.example.project_bank_account.exception.BankAccountExceptionCodes;
import com.example.project_bank_account.model.request.CreditCardApprovalRequest;
import com.example.project_bank_account.model.request.CreditCardCloseRequest;
import com.example.project_bank_account.model.request.CreditCardRequest;
import com.example.project_bank_account.model.response.CreditCardDetailsResponse;
import com.example.project_bank_account.model.response.CreditCardResponse;
import com.example.project_bank_account.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepo creditCardRepo;

    private final BankAccountRepo bankAccountRepo;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepo creditCardRepository, BankAccountRepo bankAccountRepo) {
        this.creditCardRepo = creditCardRepository;
        this.bankAccountRepo = bankAccountRepo;
    }

    @Override
    public CreditCardResponse applyForCreditCard(CreditCardRequest request) {
        CreditCard creditCard = new CreditCard();
        creditCard.setBankAccount(bankAccountRepo.findByBankAccountNumber(request.getBankAccountNumber())
                .orElseThrow(() -> new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_NOT_FOUND)));
        creditCard.setCardType(CardType.CREDIT);

        CreditCard savedCreditCard = creditCardRepo.save(creditCard);

        return CreditCardResponse.builder()
                .id(savedCreditCard.getUuid())
                .bankAccountNumber(savedCreditCard.getBankAccount().getBankAccountNumber())
                .cardType(savedCreditCard.getCardType())
                .build();
    }

    @Override
    public CreditCardResponse approveCreditCard(CreditCardApprovalRequest request) {


        CreditCard creditCard = creditCardRepo.findById(request.getCardId())
                .orElseThrow(() -> new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_CREDIT_CARD_NOT_FOUND, request.getCardId().toString()));

        creditCardRepo.save(creditCard);

        return CreditCardResponse.builder()
                .id(creditCard.getUuid())
                .bankAccountNumber(creditCard.getBankAccount().getBankAccountNumber())
                .cardType(creditCard.getCardType())
                .build();
    }

    @Override
    public CreditCardResponse closeCreditCard(CreditCardCloseRequest request) {
        CreditCard creditCard = creditCardRepo.findById(request.getCardId())
                .orElseThrow(() -> new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_CREDIT_CARD_NOT_FOUND, request.getCardId().toString()));

        creditCardRepo.save(creditCard);

        return CreditCardResponse.builder()
                .id(creditCard.getUuid())
                .bankAccountNumber(creditCard.getBankAccount().getBankAccountNumber())
                .cardType(creditCard.getCardType())
                .build();
    }

    @Override
    public CreditCardDetailsResponse getCreditCardDetails(UUID cardId) {
        CreditCard creditCard = creditCardRepo.findById(cardId)
                .orElseThrow(() -> new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_CREDIT_CARD_NOT_FOUND, cardId.toString()));

        return CreditCardDetailsResponse.builder()
                .id(creditCard.getUuid())
                .bankAccountNumber(creditCard.getBankAccount().getBankAccountNumber())
                .cardType(creditCard.getCardType())
                .build();
    }

    @Override
    public List<CreditCardDetailsResponse> getCreditCardsByBankAccountNumber(String bankAccountNumber) {
        List<CreditCard> creditCards = creditCardRepo.findByBankAccountBankAccountNumber(bankAccountNumber);
        return creditCards.stream()
                .map(this::mapToCreditCardDetailsResponse)
                .collect(Collectors.toList());
    }

    private CreditCardDetailsResponse mapToCreditCardDetailsResponse(CreditCard creditCard) {
        return CreditCardDetailsResponse.builder()
                .id(creditCard.getUuid())
                .bankAccountNumber(creditCard.getBankAccount().getBankAccountNumber())
                .cardType(creditCard.getCardType())
                .build();
    }
}