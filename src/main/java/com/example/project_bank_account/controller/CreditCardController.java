package com.example.project_bank_account.controller;

import com.example.project_bank_account.data.repo.BankAccountRepo;
import com.example.project_bank_account.model.request.*;
import com.example.project_bank_account.model.response.*;
import com.example.project_bank_account.service.BankAccountLoanService;
import com.example.project_bank_account.service.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Credit Cards", description = "Provides Credit Cards APIs")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/apply")
    public ResponseEntity<CreditCardResponse> applyForCreditCard(@RequestBody @Valid CreditCardRequest request) {
        CreditCardResponse response = creditCardService.applyForCreditCard(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve")
    public ResponseEntity<CreditCardResponse> approveCreditCard(@RequestBody @Valid CreditCardApprovalRequest request) {
        CreditCardResponse response = creditCardService.approveCreditCard(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/close")
    public ResponseEntity<CreditCardResponse> closeCreditCard(@RequestBody @Valid CreditCardCloseRequest request) {
        CreditCardResponse response = creditCardService.closeCreditCard(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details/{cardId}")
    public ResponseEntity<CreditCardDetailsResponse> getCreditCardDetails(@PathVariable UUID cardId) {
        CreditCardDetailsResponse response = creditCardService.getCreditCardDetails(cardId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/details/account/{bankAccountNumber}")
    public ResponseEntity<List<CreditCardDetailsResponse>> getCreditCardsByBankAccountNumber(@PathVariable String bankAccountNumber) {
        List<CreditCardDetailsResponse> creditCards = creditCardService.getCreditCardsByBankAccountNumber(bankAccountNumber);
        return new ResponseEntity<>(creditCards, HttpStatus.OK);
    }
}