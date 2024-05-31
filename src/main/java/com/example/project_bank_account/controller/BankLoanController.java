package com.example.project_bank_account.controller;

import com.example.project_bank_account.model.request.BankAccountLoanApprovalRequest;
import com.example.project_bank_account.model.request.BankAccountLoanCloseRequest;
import com.example.project_bank_account.model.request.BankAccountLoanRequest;
import com.example.project_bank_account.model.response.BankAccountLoanDetailsResponse;
import com.example.project_bank_account.model.response.BankAccountLoanResponse;
import com.example.project_bank_account.model.response.ErrorResponse;
import com.example.project_bank_account.service.BankAccountLoanService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "BankAccountLoan", description = "Provides Loan APIs")
public class BankLoanController {

    @Autowired
    private BankAccountLoanService bankAccountLoanService;

    @Operation(summary = "Apply for Loan REST API", description = "Allows a user to apply for a loan.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @PostMapping("/apply")
    public ResponseEntity<BankAccountLoanResponse> applyForLoan(@RequestBody @Valid BankAccountLoanRequest loanRequest) {
        BankAccountLoanResponse loanResponse = bankAccountLoanService.applyForLoan(loanRequest);
        return ResponseEntity.ok(loanResponse);
    }

    @Operation(summary = "Approve Loan REST API", description = "Allows an admin to approve a loan application.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "404", description = "Loan Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/approve")
    public ResponseEntity<BankAccountLoanResponse> approveLoan(@RequestBody @Valid BankAccountLoanApprovalRequest loanApprovalRequest) {
        BankAccountLoanResponse loanResponse = bankAccountLoanService.approveLoan(loanApprovalRequest);
        return ResponseEntity.ok(loanResponse);
    }

    @Operation(summary = "Close Loan REST API", description = "Allows a user to close a loan.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "404", description = "Loan Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/close")
    public ResponseEntity<BankAccountLoanResponse> closeLoan(@RequestBody @Valid BankAccountLoanCloseRequest loanCloseRequest) {
        BankAccountLoanResponse loanResponse = bankAccountLoanService.closeLoan(loanCloseRequest);
        return ResponseEntity.ok(loanResponse);
    }

    @Operation(summary = "Get Loan Details by Account Number REST API", description = "Fetches all loan details for a given account number.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/details/account/{bankAccountNumber}")
    public ResponseEntity<List<BankAccountLoanDetailsResponse>> getLoanDetailsByAccountNumber(@PathVariable String bankAccountNumber) {
        List<BankAccountLoanDetailsResponse> loanDetails = bankAccountLoanService.getLoanDetailsByAccountNumber(bankAccountNumber);
        return ResponseEntity.ok(loanDetails);
    }

    @Operation(summary = "Get Loan Details by Loan ID REST API", description = "Fetches loan details for a given loan ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "404", description = "Loan Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/details/{loanId}")
    public ResponseEntity<BankAccountLoanDetailsResponse> getLoanDetailsByLoanId(@PathVariable UUID loanId) {
        BankAccountLoanDetailsResponse loanDetails = bankAccountLoanService.getLoanDetailsByLoanId(loanId);
        return ResponseEntity.ok(loanDetails);
    }
}