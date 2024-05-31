package com.example.project_bank_account.service.impl;

import com.example.project_bank_account.data.domain.BankAccount;
import com.example.project_bank_account.data.domain.BankAccountLoan;
import com.example.project_bank_account.data.repo.BankAccountLoanRepo;
import com.example.project_bank_account.data.repo.BankAccountRepo;
import com.example.project_bank_account.exception.BankAccountException;
import com.example.project_bank_account.exception.BankAccountExceptionCodes;
import com.example.project_bank_account.model.request.BankAccountLoanApprovalRequest;
import com.example.project_bank_account.model.request.BankAccountLoanCloseRequest;
import com.example.project_bank_account.model.request.BankAccountLoanRequest;
import com.example.project_bank_account.model.response.BankAccountLoanDetailsResponse;
import com.example.project_bank_account.model.response.BankAccountLoanResponse;
import com.example.project_bank_account.service.BankAccountLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.math.RoundingMode;

@Service
public class BankAccountLoanServiceImpl implements BankAccountLoanService {

    @Autowired
    private BankAccountLoanRepo bankAccountLoanRepo;

    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Override
    public BankAccountLoanResponse applyForLoan(BankAccountLoanRequest loanRequest) {
        Optional<BankAccount> bankAccounts = bankAccountRepo.findByBankAccountNumber(loanRequest.getBankAccountNumber());
        if (bankAccounts.isEmpty()) {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_NOT_FOUND);
        }

        BigDecimal monthlyInstallment = calculateMonthlyInstallment(loanRequest.getLoanAmount(), loanRequest.getLoanTermInMonths());

        BankAccountLoan loan = new BankAccountLoan();
        loan.setBankAccount(bankAccounts.get());
        loan.setLoanType(loanRequest.getLoanType());
        loan.setLoanAmount(loanRequest.getLoanAmount());
        loan.setApprovedDate(null);
        loan.setLoanTermInMonths(loanRequest.getLoanTermInMonths());
        loan.setMonthlyInstallment(monthlyInstallment);
        loan.setLoanStatus("PENDING");

        bankAccountLoanRepo.save(loan);

        return BankAccountLoanResponse.builder()
                .id(loan.getUuid())
                .bankAccountNumber(loan.getBankAccount().getBankAccountNumber())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .loanTermInMonths(loan.getLoanTermInMonths())
                .monthlyInstallment(monthlyInstallment)
                .loanStatus(loan.getLoanStatus())
                .build();
    }


    @Override
    public BankAccountLoanResponse approveLoan(BankAccountLoanApprovalRequest loanApprovalRequest) {
        Optional<BankAccountLoan> loanOptional = bankAccountLoanRepo.findById(loanApprovalRequest.getLoanId());
        if (loanOptional.isEmpty()) {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_LOAN_NOT_FOUND);
        }

        BankAccountLoan loan = loanOptional.get();
        loan.setApprovedDate(LocalDateTime.now());
        loan.setLoanStatus("APPROVED");
        bankAccountLoanRepo.save(loan);

        return BankAccountLoanResponse.builder()
                .id(loan.getUuid())
                .bankAccountNumber(loan.getBankAccount().getBankAccountNumber())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .approvedDate(loan.getApprovedDate())
                .loanTermInMonths(loan.getLoanTermInMonths())
                .monthlyInstallment(loan.getMonthlyInstallment())
                .loanStatus(loan.getLoanStatus())
                .build();
    }

    @Override
    public BankAccountLoanResponse closeLoan(BankAccountLoanCloseRequest loanCloseRequest) {
        Optional<BankAccountLoan> loanOptional = bankAccountLoanRepo.findById(loanCloseRequest.getLoanId());
        if (loanOptional.isEmpty()) {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_LOAN_NOT_FOUND);
        }

        BankAccountLoan loan = loanOptional.get();
        loan.setLoanStatus("CLOSED");
        bankAccountLoanRepo.save(loan);

        return BankAccountLoanResponse.builder()
                .id(loan.getUuid())
                .bankAccountNumber(loan.getBankAccount().getBankAccountNumber())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .approvedDate(loan.getApprovedDate())
                .loanTermInMonths(loan.getLoanTermInMonths())
                .monthlyInstallment(loan.getMonthlyInstallment())
                .loanStatus(loan.getLoanStatus())
                .build();
    }

    @Override
    public List<BankAccountLoanDetailsResponse> getLoanDetailsByAccountNumber(String bankAccountNumber) {
        List<BankAccountLoan> loans = bankAccountLoanRepo.findByBankAccountBankAccountNumber(bankAccountNumber);

        return loans.stream()
                .map(loan -> BankAccountLoanDetailsResponse.builder()
                        .loanId(loan.getUuid())
                        .loanType(loan.getLoanType())
                        .loanAmount(loan.getLoanAmount())
                        .sanctionedDate(loan.getApprovedDate())
                        .loanTermInMonths(loan.getLoanTermInMonths())
                        .monthlyInstallment(loan.getMonthlyInstallment())
                        .loanStatus(loan.getLoanStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountLoanDetailsResponse getLoanDetailsByLoanId(UUID loanId) {
        Optional<BankAccountLoan> loanOptional = bankAccountLoanRepo.findById(loanId);
        if (loanOptional.isEmpty()) {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_LOAN_NOT_FOUND);
        }

        BankAccountLoan loan = loanOptional.get();
        return BankAccountLoanDetailsResponse.builder()
                .loanId(loan.getUuid())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .sanctionedDate(loan.getApprovedDate())
                .loanTermInMonths(loan.getLoanTermInMonths())
                .monthlyInstallment(loan.getMonthlyInstallment())
                .loanStatus(loan.getLoanStatus())
                .build();
    }

    private BigDecimal calculateMonthlyInstallment(BigDecimal loanAmount, int loanTermInMonths) {
        return loanAmount.divide(new BigDecimal(loanTermInMonths), RoundingMode.HALF_UP);
    }
}