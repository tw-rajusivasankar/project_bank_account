package com.example.project_bank_account.controller;

import com.example.project_bank_account.enums.LoanType;
import com.example.project_bank_account.exception.BankAccountException;
import com.example.project_bank_account.exception.BankAccountExceptionCodes;
import com.example.project_bank_account.model.request.BankAccountLoanApprovalRequest;
import com.example.project_bank_account.model.request.BankAccountLoanCloseRequest;
import com.example.project_bank_account.model.request.BankAccountLoanRequest;
import com.example.project_bank_account.model.response.BankAccountLoanDetailsResponse;
import com.example.project_bank_account.model.response.BankAccountLoanResponse;
import com.example.project_bank_account.service.BankAccountLoanService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountLoanControllerTests {

    private static final String BANK_ACCOUNT_NUMBER = "1234567890";
    private static final UUID LOAN_ID = UUID.randomUUID();
    @Mock
    private BankAccountLoanService bankAccountLoanService;
    @InjectMocks
    private BankLoanController bankLoanController;

    public static BankAccountLoanResponse bankAccountLoanMockResponse() {
        return BankAccountLoanResponse.builder()
                .id(UUID.fromString("3e5bc260-03f6-4bd0-a9cf-648d16fe86b0"))
                .bankAccountNumber("1234567890")
                .loanType(LoanType.VEHICLE)
                .loanAmount(new BigDecimal("50000.00"))
                .loanTermInMonths(120)
                .monthlyInstallment(new BigDecimal("600.00"))
                .loanStatus("APPROVED")
                .build();
    }

    public static BankAccountLoanDetailsResponse bankAccountLoanDetailsMockResponse() {
        return BankAccountLoanDetailsResponse.builder()
                .loanId(UUID.fromString("f4d00ca0-47bb-40d4-aa13-96cbdbc7c85c"))
                .loanType(LoanType.VEHICLE)
                .loanAmount(new BigDecimal("10000.00"))
                .loanTermInMonths(12)
                .monthlyInstallment(new BigDecimal("850.00"))
                .loanStatus("APPROVED")
                .build();
    }

    public static BankAccountLoanDetailsResponse createMockResponse() {
        return BankAccountLoanDetailsResponse.builder()
                .loanId(UUID.randomUUID())
                .loanType(LoanType.AGRICULTURE)
                .loanAmount(new BigDecimal("10000.00"))
                .sanctionedDate(LocalDateTime.now())
                .loanTermInMonths(36)
                .monthlyInstallment(new BigDecimal("300.00"))
                .loanStatus("APPROVED")
                .build();
    }

    @Test
    @Order(1)
    void shouldApplyForLoanSuccessfully() {
        when(bankAccountLoanService.applyForLoan(any(BankAccountLoanRequest.class))).thenReturn(bankAccountLoanMockResponse());

        BankAccountLoanRequest request = new BankAccountLoanRequest();

        ResponseEntity<BankAccountLoanResponse> responseEntity = bankLoanController.applyForLoan(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(bankAccountLoanMockResponse(), responseEntity.getBody());

        verify(bankAccountLoanService, times(1)).applyForLoan(request);
    }

    @Test
    @Order(2)
    void shouldApproveLoanSuccessfully() {
        BankAccountLoanApprovalRequest request = new BankAccountLoanApprovalRequest();
        when(bankAccountLoanService.approveLoan(request)).thenReturn(bankAccountLoanMockResponse());

        ResponseEntity<BankAccountLoanResponse> responseEntity = bankLoanController.approveLoan(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(bankAccountLoanMockResponse(), responseEntity.getBody());

        verify(bankAccountLoanService, times(1)).approveLoan(request);
    }

    @Test
    @Order(3)
    void shouldCloseLoanSuccessfully() {
        BankAccountLoanCloseRequest request = new BankAccountLoanCloseRequest();
        when(bankAccountLoanService.closeLoan(request)).thenReturn(bankAccountLoanMockResponse());

        ResponseEntity<BankAccountLoanResponse> responseEntity = bankLoanController.closeLoan(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(bankAccountLoanMockResponse(), responseEntity.getBody());

        verify(bankAccountLoanService, times(1)).closeLoan(request);
    }

    @Test
    @Order(4)
    void shouldGetLoanDetailsByAccountNumberSuccessfully() {
        when(bankAccountLoanService.getLoanDetailsByAccountNumber(anyString())).thenReturn(List.of(bankAccountLoanDetailsMockResponse()));

        ResponseEntity<List<BankAccountLoanDetailsResponse>> responseEntity =
                bankLoanController.getLoanDetailsByAccountNumber(BANK_ACCOUNT_NUMBER);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(List.of(bankAccountLoanDetailsMockResponse()), responseEntity.getBody());

        verify(bankAccountLoanService, times(1)).getLoanDetailsByAccountNumber(BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Order(5)
    void shouldGetLoanDetailsByLoanIdSuccessfully() {
        when(bankAccountLoanService.getLoanDetailsByLoanId(any(UUID.class))).thenReturn(bankAccountLoanDetailsMockResponse());

        ResponseEntity<BankAccountLoanDetailsResponse> responseEntity =
                bankLoanController.getLoanDetailsByLoanId(LOAN_ID);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(bankAccountLoanDetailsMockResponse(), responseEntity.getBody());

        verify(bankAccountLoanService, times(1)).getLoanDetailsByLoanId(LOAN_ID);
    }

    @Test
    @Order(6)
    void shouldThrowExceptionWhenApplyingForLoan() {
        when(bankAccountLoanService.applyForLoan(any(BankAccountLoanRequest.class))).thenThrow(new RuntimeException("Failed to apply for loan"));
        BankAccountLoanRequest request = new BankAccountLoanRequest();
        Assertions.assertThrows(RuntimeException.class, () -> bankLoanController.applyForLoan(request));
        verify(bankAccountLoanService, times(1)).applyForLoan(request);
    }

    @Test
    @Order(7)
    void shouldThrowExceptionWhenApprovingLoan() {
        BankAccountLoanApprovalRequest request = new BankAccountLoanApprovalRequest();
        when(bankAccountLoanService.approveLoan(request)).thenThrow(new RuntimeException("Failed to approve loan"));
        Assertions.assertThrows(RuntimeException.class, () -> bankLoanController.approveLoan(request));
        verify(bankAccountLoanService, times(1)).approveLoan(request);
    }

    @Test
    @Order(8)
    void shouldThrowExceptionWhenClosingLoan() {
        BankAccountLoanCloseRequest request = new BankAccountLoanCloseRequest();
        when(bankAccountLoanService.closeLoan(request)).thenThrow(new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_LOAN_NOT_FOUND));
        Assertions.assertThrows(RuntimeException.class, () -> bankLoanController.closeLoan(request));
        verify(bankAccountLoanService, times(1)).closeLoan(request);
    }

    @Test
    @Order(9)
    void shouldThrowExceptionWhenGettingLoanDetailsByAccountNumber() {
        when(bankAccountLoanService.getLoanDetailsByAccountNumber(anyString())).thenThrow(new RuntimeException("Failed to get loan details by account number"));
        Assertions.assertThrows(RuntimeException.class, () -> bankLoanController.getLoanDetailsByAccountNumber(BANK_ACCOUNT_NUMBER));
        verify(bankAccountLoanService, times(1)).getLoanDetailsByAccountNumber(BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Order(10)
    void shouldThrowExceptionWhenGettingLoanDetailsByLoanId() {
        when(bankAccountLoanService.getLoanDetailsByLoanId(any(UUID.class))).thenThrow(new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_LOAN_NOT_FOUND));
        Assertions.assertThrows(RuntimeException.class, () -> bankLoanController.getLoanDetailsByLoanId(LOAN_ID));
        verify(bankAccountLoanService, times(1)).getLoanDetailsByLoanId(LOAN_ID);
    }
}