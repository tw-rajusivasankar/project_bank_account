package com.example.project_bank_account.service;

import com.example.project_bank_account.model.request.BankAccountLoanApprovalRequest;
import com.example.project_bank_account.model.request.BankAccountLoanCloseRequest;
import com.example.project_bank_account.model.request.BankAccountLoanRequest;
import com.example.project_bank_account.model.response.BankAccountLoanDetailsResponse;
import com.example.project_bank_account.model.response.BankAccountLoanResponse;

import java.util.List;
import java.util.UUID;

public interface BankAccountLoanService {

    BankAccountLoanResponse applyForLoan(BankAccountLoanRequest loanRequest);
    BankAccountLoanResponse approveLoan(BankAccountLoanApprovalRequest loanApprovalRequest);
    BankAccountLoanResponse closeLoan(BankAccountLoanCloseRequest loanCloseRequest);
    List<BankAccountLoanDetailsResponse> getLoanDetailsByAccountNumber(String bankAccountNumber);
    BankAccountLoanDetailsResponse getLoanDetailsByLoanId(UUID loanId);
}