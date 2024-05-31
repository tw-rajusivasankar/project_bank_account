package com.example.project_bank_account.controller;

import com.example.project_bank_account.model.request.*;
import com.example.project_bank_account.model.response.*;
import com.example.project_bank_account.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankAccount")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "BankAccount", description = "Provides Bank Account Api's")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Operation(summary = "Create Bank Account REST API", description = "Creates a new bank account.")
    @ApiResponse(responseCode = "200", description = "Account created successfully")
    @PostMapping("/create")
    public ResponseEntity<BankAccountResponse> createAccount(@RequestBody @Valid BankAccountCreateRequest bankAccountCreateRequest) {
        BankAccountResponse createdAccount = bankAccountService.bankAccountCreate(bankAccountCreateRequest);
        return ResponseEntity.ok(createdAccount);
    }

    @Operation(summary = "Update Bank Account REST API", description = "Updates an existing bank account.")
    @ApiResponse(responseCode = "200", description = "Account updated successfully")
    @PostMapping("/update")
    public ResponseEntity<BankAccountResponse> updateAccount(@RequestBody @Valid BankAccountUpdateRequest bankAccountUpdateRequest) {
        BankAccountResponse updatedBankAccount = bankAccountService.bankAccountUpdate(bankAccountUpdateRequest);
        return ResponseEntity.ok(updatedBankAccount);
    }

    @Operation(summary = "Login Bank Account REST API", description = "Logs in to a bank account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
    })
    @PostMapping("/login")
    public ResponseEntity<BankAccountLoginResponse> loginAccount(@RequestBody BankAccountLoginRequest bankAccountLoginRequest) {
        BankAccountLoginResponse bankAccountLoginResponse = bankAccountService.bankAccountLogin(bankAccountLoginRequest);
        return ResponseEntity.ok(bankAccountLoginResponse);
    }

    @Operation(summary = "Check Bank Account Balance REST API", description = "Checks the balance of a bank account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
    })
    @GetMapping("/balance/{bankAccountNumber}")
    public ResponseEntity<BankAccountBalanceResponse> checkBalance(@PathVariable String bankAccountNumber) {
        BankAccountBalanceResponse bankAccountBalanceResponse = bankAccountService.bankAccountCheckBalance(bankAccountNumber);
        return ResponseEntity.ok(bankAccountBalanceResponse);
    }

    @Operation(summary = "Add Money to Bank Account REST API", description = "Adds money to a bank account.")
    @ApiResponse(responseCode = "200", description = "Money added successfully")
    @PostMapping("/addMoney")
    public ResponseEntity<BankAccountMoneyResponse> updateAccount(@RequestBody @Valid BankAccountAddMoneyRequest bankAccountAddMoneyRequest) {
        BankAccountMoneyResponse bankAccountResponse = bankAccountService.bankAccountAddMoney(bankAccountAddMoneyRequest);
        return ResponseEntity.ok(bankAccountResponse);
    }

    @Operation(summary = "Withdraw Money from Bank Account REST API", description = "Withdraws money from a bank account.")
    @ApiResponse(responseCode = "200", description = "Money withdrawn successfully")
    @PostMapping("/withdrawMoney")
    public ResponseEntity<BankAccountMoneyResponse> updateAccount(@RequestBody @Valid BankAccountWithdrawMoneyRequest bankAccountWithdrawMoneyRequest) {
        BankAccountMoneyResponse bankAccountResponse = bankAccountService.bankAccountWithdrewMoney(bankAccountWithdrawMoneyRequest);
        return ResponseEntity.ok(bankAccountResponse);
    }

    @Operation(summary = "Transfer Money between Bank Accounts REST API", description = "Transfers money between two bank accounts.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Money transferred successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
    })
    @PostMapping("/transferMoney")
    public ResponseEntity<BankAccountMoneyResponse> updateAccount(@RequestBody @Valid BankAccountTransferMoneyRequest bankAccountTransferMoneyRequest) {
        BankAccountMoneyResponse bankAccountResponse = bankAccountService.bankAccountTransferMoney(bankAccountTransferMoneyRequest);
        return ResponseEntity.ok(bankAccountResponse);
    }
}