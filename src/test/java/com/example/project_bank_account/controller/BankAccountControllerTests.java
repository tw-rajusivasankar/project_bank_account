package com.example.project_bank_account.controller;

import com.example.project_bank_account.enums.BankAccountType;
import com.example.project_bank_account.enums.TransactionType;
import com.example.project_bank_account.model.request.*;
import com.example.project_bank_account.model.response.BankAccountBalanceResponse;
import com.example.project_bank_account.model.response.BankAccountLoginResponse;
import com.example.project_bank_account.model.response.BankAccountMoneyResponse;
import com.example.project_bank_account.model.response.BankAccountResponse;
import com.example.project_bank_account.utils.HttpRequestSender;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountControllerTests {

    @LocalServerPort
    int port;

    public static UUID userId = null;
    public static String password = "";
    public static String accountNumber = "";

    @Test
    @Order(1)
    void shouldReturnBankAccountSuccessfully() {
        BankAccountCreateRequest request = new BankAccountCreateRequest();
        MobileNumberRequest mobileNumberRequest = new MobileNumberRequest();

        mobileNumberRequest.setCountryCode("+115");
        mobileNumberRequest.setNumber("996129766");
        request.setUserName("Ravindra");
        request.setAge(26);
        request.setUserMobileNumber(mobileNumberRequest);
        request.setEmailAddress("ravindra@gmail.com");
        request.setPassword("iamHere@12345");
        request.setConfirmPassword("iamHere@12345");
        request.setAddress("Tirupati");
        request.setPincode("517101");
        request.setBankAccountType(BankAccountType.SAVINGS);

        ResponseEntity<BankAccountResponse> response = HttpRequestSender.sendPostRequest(
                "/bankAccount/create",
                port,
                request,
                new ParameterizedTypeReference<BankAccountResponse>() {
                }
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        BankAccountResponse bankAccountResponse = response.getBody();

        Assertions.assertNotNull(bankAccountResponse.getUserId());
        Assertions.assertNotNull(bankAccountResponse.getUserPassword());
        Assertions.assertEquals("Ravindra", bankAccountResponse.getUserName());
        Assertions.assertEquals("+115996129766", bankAccountResponse.getUserMobileNumber());
        Assertions.assertTrue(bankAccountResponse.isActive());
        Assertions.assertNotNull(bankAccountResponse.getCreatedDateTime());
        Assertions.assertNotNull(bankAccountResponse.getModifiedDateTime());
        Assertions.assertEquals(BankAccountType.SAVINGS, bankAccountResponse.getBankAccountType());
        Assertions.assertNotNull(bankAccountResponse.getBankAccountNumber());
        Assertions.assertEquals(0, bankAccountResponse.getAmount().intValue());

        userId = bankAccountResponse.getUserId();
        password = bankAccountResponse.getUserPassword();
        accountNumber = bankAccountResponse.getBankAccountNumber();
    }

    @Test
    @Order(2)
    void shouldThrowExceptionForInvalidMobileNumber() {
        BankAccountCreateRequest request = new BankAccountCreateRequest();
        MobileNumberRequest mobileNumberRequest = new MobileNumberRequest();

        mobileNumberRequest.setCountryCode("+115");
        mobileNumberRequest.setNumber("123456");

        request.setUserName("Ravindra");
        request.setAge(26);
        request.setUserMobileNumber(mobileNumberRequest);
        request.setEmailAddress("ravindra@gmail.com");
        request.setPassword("iamHere@12345");
        request.setConfirmPassword("iamHere@12345");
        request.setAddress("Tirupati");
        request.setPincode("517101");
        request.setBankAccountType(BankAccountType.SAVINGS);

        ResponseEntity<BankAccountResponse> response = HttpRequestSender.sendPostRequest(
                "/bankAccount/create",
                port,
                request,
                new ParameterizedTypeReference<BankAccountResponse>() {
                }
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        // Validate exception scenario
        BankAccountResponse bankAccountResponse = response.getBody();
        Assertions.assertNull(bankAccountResponse.getUserId()); // Assuming user ID is null in case of exception
        Assertions.assertNull(bankAccountResponse.getBankAccountNumber()); // Assuming account number is null in case of exception
        Assertions.assertNull(bankAccountResponse.getAmount()); // Assuming amount is null in case of exception
    }

    @Test
    @Order(3)
    void shouldUpdateBankAccountSuccessfully() {
        BankAccountUpdateRequest request = new BankAccountUpdateRequest();
        request.setBankAccountNumber(accountNumber);
        request.setUserName("Ravinder Updated");
        request.setAge(26);
        request.setEmailAddress("ravindra@gmail.com");
        request.setPassword("iamHere@12345");
        request.setConfirmPassword("iamHere@12345");
        request.setAddress("Tirupati");
        request.setPincode("517101");
        MobileNumberRequest mobileNumberRequest = new MobileNumberRequest();

        mobileNumberRequest.setCountryCode("+115");
        mobileNumberRequest.setNumber("996129768");
        request.setUserMobileNumber(mobileNumberRequest);

        ResponseEntity<BankAccountResponse> response = HttpRequestSender.sendPostRequest(
                "/bankAccount/update",
                port,
                request,
                new ParameterizedTypeReference<BankAccountResponse>() {}
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        BankAccountResponse bankAccountResponse = response.getBody();
        Assertions.assertNotNull(bankAccountResponse.getUserId());
        Assertions.assertEquals("Ravinder Updated", bankAccountResponse.getUserName());
        Assertions.assertEquals("+115996129768", bankAccountResponse.getUserMobileNumber());
    }

    @Test
    @Order(4)
    void shouldThrowExceptionWhenUserIdOrPasswordNotThere() {
        BankAccountLoginRequest request = new BankAccountLoginRequest();
        request.setUserId(userId);
        request.setPassword(password);

        ResponseEntity<BankAccountLoginResponse> response = HttpRequestSender.sendPostRequest(
                "/bankAccount/login",
                port,
                request,
                new ParameterizedTypeReference<BankAccountLoginResponse>() {}
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(5)
    void shouldCheckBalanceSuccessfully() {
        ResponseEntity<BankAccountBalanceResponse> response = HttpRequestSender.sendGetRequest(
                "/bankAccount/balance/" + accountNumber,
                port,
                new ParameterizedTypeReference<BankAccountBalanceResponse>() {}
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        BankAccountBalanceResponse balanceResponse = response.getBody();
        Assertions.assertEquals(accountNumber, balanceResponse.getBankAccountNumber());
        Assertions.assertEquals(BigDecimal.ZERO, balanceResponse.getMoney());
    }

    @Test
    @Order(6)
    void shouldAddMoneySuccessfully() {
        BankAccountAddMoneyRequest request = new BankAccountAddMoneyRequest();
        request.setBankAccountNumber(accountNumber);
        request.setAmount(new BigDecimal("100"));

        ResponseEntity<BankAccountMoneyResponse> response = HttpRequestSender.sendPostRequest(
                "/bankAccount/addMoney",
                port,
                request,
                new ParameterizedTypeReference<BankAccountMoneyResponse>() {
                }
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        BankAccountMoneyResponse moneyResponse = response.getBody();
        Assertions.assertEquals(new BigDecimal("100"), moneyResponse.getCurrentBalance());
    }


    @Test
    @Order(7)
    void shouldWithdrawMoneySuccessfully() {
        BankAccountWithdrawMoneyRequest request = new BankAccountWithdrawMoneyRequest();
        request.setBankAccountNumber(accountNumber);
        request.setAmount(new BigDecimal("50"));

        ResponseEntity<BankAccountMoneyResponse> response = HttpRequestSender.sendPostRequest(
                "/bankAccount/withdrawMoney",
                port,
                request,
                new ParameterizedTypeReference<BankAccountMoneyResponse>() {}
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        BankAccountMoneyResponse moneyResponse = response.getBody();
        Assertions.assertEquals(TransactionType.WITHDRAWAL, moneyResponse.getTransactionType());
        Assertions.assertNotNull(moneyResponse.getCurrentBalance());
    }
}