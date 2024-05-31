package com.example.project_bank_account.service.impl;

import com.example.project_bank_account.data.domain.BankAccount;
import com.example.project_bank_account.data.domain.BankAccountTransaction;
import com.example.project_bank_account.data.repo.BankAccountRepo;
import com.example.project_bank_account.data.repo.BankTransactionRepo;
import com.example.project_bank_account.enums.BankAccountType;
import com.example.project_bank_account.enums.TransactionType;
import com.example.project_bank_account.exception.BankAccountException;
import com.example.project_bank_account.exception.BankAccountExceptionCodes;
import com.example.project_bank_account.model.request.*;
import com.example.project_bank_account.model.response.BankAccountBalanceResponse;
import com.example.project_bank_account.model.response.BankAccountLoginResponse;
import com.example.project_bank_account.model.response.BankAccountMoneyResponse;
import com.example.project_bank_account.model.response.BankAccountResponse;
import com.example.project_bank_account.service.BankAccountService;
import com.example.project_bank_account.util.BankAccountToLoginResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Autowired
    private BankTransactionRepo bankTransactionRepo;

    @Autowired
    private BankAccountToLoginResponseConverter converter;

    @Override
    public BankAccountResponse bankAccountCreate(BankAccountCreateRequest bankAccountCreateRequest) {
        if(bankAccountCreateRequest != null && bankAccountCreateRequest.getUserMobileNumber().getNumber() != null) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setActive(true);
            bankAccount.setUserName(bankAccountCreateRequest.getUserName());
            bankAccount.setUserMobileNumber(bankAccountCreateRequest.getUserMobileNumber().getCountryCode() + bankAccountCreateRequest.getUserMobileNumber().getNumber());
            bankAccount.setUserPassword(bankAccountCreateRequest.getPassword());
            bankAccount.setAge(bankAccountCreateRequest.getAge());
            bankAccount.setEmailAddress(bankAccountCreateRequest.getEmailAddress());
            bankAccount.setAddress(bankAccountCreateRequest.getAddress());
            bankAccount.setPincode(bankAccountCreateRequest.getPincode());
            bankAccount.setType(bankAccountCreateRequest.getBankAccountType().toString());
            bankAccount.setBankAccountNumber(generateBankAccountNumber());
            bankAccount.setAmount(BigDecimal.ZERO);
            bankAccount = bankAccountRepo.save(bankAccount);

            return BankAccountResponse.builder()
                    .userId(bankAccount.getUuid())
                    .userName(bankAccount.getUserName())
                    .userMobileNumber(bankAccount.getUserMobileNumber())
                    .userPassword(bankAccount.getUserPassword())
                    .isActive(bankAccount.isActive())
                    .createdDateTime(bankAccount.getCreatedDateTime())
                    .modifiedDateTime(bankAccount.getModifiedDateTime())
                    .bankAccountType(getBankAccountType(bankAccount.getType()))
                    .bankAccountNumber(bankAccount.getBankAccountNumber())
                    .amount(bankAccount.getAmount())
                    .build();
        } else {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_MOBILE_EMPTY);
        }
    }

    @Override
    public BankAccountResponse bankAccountUpdate(BankAccountUpdateRequest bankAccountUpdateRequest) {
        if (bankAccountUpdateRequest.getBankAccountNumber() != null) {
            Optional<BankAccount> optionalBankAccount = bankAccountRepo.findByBankAccountNumber(bankAccountUpdateRequest.getBankAccountNumber());
            if (optionalBankAccount.isPresent()) {
                BankAccount bankAccount = optionalBankAccount.get();

                bankAccount.setUserName(bankAccountUpdateRequest.getUserName());
                bankAccount.setAge(bankAccountUpdateRequest.getAge());
                bankAccount.setUserMobileNumber(bankAccountUpdateRequest.getUserMobileNumber().getCountryCode() + bankAccountUpdateRequest.getUserMobileNumber().getNumber());
                bankAccount.setEmailAddress(bankAccountUpdateRequest.getEmailAddress());
                bankAccount.setUserPassword(bankAccountUpdateRequest.getPassword());
                bankAccount.setAddress(bankAccountUpdateRequest.getAddress());
                bankAccount.setPincode(bankAccountUpdateRequest.getPincode());
                bankAccount.setAge(bankAccountUpdateRequest.getAge());
                bankAccount.setModifiedDateTime(LocalDateTime.now());

                bankAccountRepo.save(bankAccount);

                return BankAccountResponse.builder()
                        .userId(bankAccount.getUuid())
                        .userName(bankAccount.getUserName())
                        .userMobileNumber(bankAccount.getUserMobileNumber())
                        .userPassword(bankAccount.getUserPassword())
                        .isActive(bankAccount.isActive())
                        .createdDateTime(bankAccount.getCreatedDateTime())
                        .modifiedDateTime(bankAccount.getModifiedDateTime())
                        .bankAccountType(getBankAccountType(bankAccount.getType()))
                        .bankAccountNumber(bankAccount.getBankAccountNumber())
                        .build();
            } else {
                throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INVALID_ACCOUNT_NUMBER);
            }
        } else {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INVALID_ACCOUNT_NUMBER);
        }
    }

    private BankAccountType getBankAccountType(String type) {
        if (type == BankAccountType.SAVINGS.name()) {
            return BankAccountType.SAVINGS;
        } else {
            return BankAccountType.NORMAL;
        }
    }

    public String generateBankAccountNumber() {
        Random random = new Random();
        String accountNumber;

        do {
            StringBuilder accountNumberBuilder = new StringBuilder();

            accountNumberBuilder.append(random.nextInt(9) + 1);

            for (int i = 0; i < 14; i++) {
                accountNumberBuilder.append(random.nextInt(10));
            }

            accountNumber = accountNumberBuilder.toString();
        } while (bankAccountRepo.existsByBankAccountNumber(accountNumber));

        return accountNumber;
    }

    @Override
    public BankAccountLoginResponse bankAccountLogin(BankAccountLoginRequest bankAccountLoginRequest) {
        if(bankAccountLoginRequest != null) {
           Optional<BankAccount> bankAccountLoginResponse = bankAccountRepo.findByUserIdAndPassword(bankAccountLoginRequest.getUserId(), bankAccountLoginRequest.getPassword());
           if(bankAccountLoginResponse.isPresent()) {
               BankAccount bankAccount = bankAccountLoginResponse.get();
               return converter.convert(bankAccount);
           } else {
               return BankAccountLoginResponse.builder()
                       .isLoggedIn(false)
                       .isActive(false)
                       .build();
           }
        } else {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_LOGIN_CREDENTIALS_REQUIRED);
        }
    }

    @Override
    public BankAccountBalanceResponse bankAccountCheckBalance(String bankAccountNumber) {
        if(bankAccountNumber != null) {
            BankAccount bankAccount;
            Optional<BankAccount> optionalBankAccount = bankAccountRepo.findByBankAccountNumber(bankAccountNumber);
            if (optionalBankAccount.isPresent()) {
                bankAccount = optionalBankAccount.get();
                return BankAccountBalanceResponse.builder()
                        .bankAccountNumber(bankAccount.getBankAccountNumber())
                        .money(bankAccount.getAmount())
                        .checkedTime(LocalDateTime.now())
                        .build();
            } else {
                return null;
            }
        } else {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INVALID_ACCOUNT_NUMBER);
        }
    }

    @Override
    @Transactional
    public BankAccountMoneyResponse bankAccountAddMoney(BankAccountAddMoneyRequest bankAccountAddMoneyRequest) {
        if(bankAccountAddMoneyRequest.getBankAccountNumber() != null) {
            Optional<BankAccount> optionalBankAccount = bankAccountRepo.findByBankAccountNumber(bankAccountAddMoneyRequest.getBankAccountNumber());
            if (optionalBankAccount.isPresent()) {
                BankAccount bankAccount = optionalBankAccount.get();
                BigDecimal currentBalance = bankAccount.getAmount();
                BigDecimal amountToAdd = bankAccountAddMoneyRequest.getAmount();
                BigDecimal newBalance = currentBalance.add(amountToAdd);
                bankAccount.setAmount(newBalance);
                bankAccountRepo.save(bankAccount);

                BankAccountTransaction transaction = new BankAccountTransaction();
                transaction.setAmount(amountToAdd);
                transaction.setTransactionTime(LocalDateTime.now());
                transaction.setTransactionType(TransactionType.DEPOSIT);
                transaction.setBankAccount(bankAccount);
                transaction = bankTransactionRepo.save(transaction);

                BankAccountMoneyResponse response = BankAccountMoneyResponse.builder()
                        .bankAccountNumber(bankAccount.getBankAccountNumber())
                        .currentBalance(transaction.getAmount())
                        .transactionTime(LocalDateTime.now())
                        .transactionType(TransactionType.DEPOSIT)
                        .message("Amount deposited successfully.")
                        .build();

                return response;
            } else {
                throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INVALID_ACCOUNT_NUMBER);
            }
        } else {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INVALID_ACCOUNT_NUMBER);
        }
    }

    @Override
    @Transactional
    public BankAccountMoneyResponse bankAccountWithdrewMoney(BankAccountWithdrawMoneyRequest bankAccountWithdrawMoneyRequest) {
        if (bankAccountWithdrawMoneyRequest.getBankAccountNumber() != null) {
            Optional<BankAccount> optionalBankAccount = bankAccountRepo.findByBankAccountNumber(bankAccountWithdrawMoneyRequest.getBankAccountNumber());
            if (optionalBankAccount.isPresent()) {
                BankAccount bankAccount = optionalBankAccount.get();
                BigDecimal currentBalance = bankAccount.getAmount();
                BigDecimal withdrawalAmount = bankAccountWithdrawMoneyRequest.getAmount();

                if (currentBalance.compareTo(withdrawalAmount) >= 0) {
                    BigDecimal newBalance = currentBalance.subtract(withdrawalAmount);

                    bankAccount.setAmount(newBalance);
                    bankAccountRepo.save(bankAccount);

                    BankAccountTransaction bankAccountTransaction = new BankAccountTransaction();
                    bankAccountTransaction.setAmount(withdrawalAmount);
                    bankAccountTransaction.setTransactionType(TransactionType.WITHDRAWAL);
                    bankAccountTransaction.setBankAccount(bankAccount);
                    bankAccountTransaction.setTransactionTime(LocalDateTime.now());
                    bankAccountTransaction.setSourceAccountNumber(bankAccount.getBankAccountNumber());
                    bankAccountTransaction = bankTransactionRepo.save(bankAccountTransaction);

                    BigDecimal bankAccountBalance = bankAccountRepo.findByBankAccountNumber(bankAccountWithdrawMoneyRequest.getBankAccountNumber()).get().getAmount();

                    return BankAccountMoneyResponse.builder()
                            .bankAccountNumber(bankAccount.getBankAccountNumber())
                            .currentBalance(bankAccountBalance)
                            .transactionTime(LocalDateTime.now())
                            .transactionType(TransactionType.WITHDRAWAL)
                            .build();
                } else {
                    throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INSUFFICIENT_BALANCE);
                }
            } else {
                throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INVALID_ACCOUNT_NUMBER);
            }
        } else {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INVALID_ACCOUNT_NUMBER);
        }
    }

    @Override
    @Transactional
    public BankAccountMoneyResponse bankAccountTransferMoney(BankAccountTransferMoneyRequest bankAccountTransferMoneyRequest) {
        if (bankAccountTransferMoneyRequest.getSourceBankAccountNumber() == null ||
                bankAccountTransferMoneyRequest.getDestinationBankAccountNumber() == null) {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INVALID_ACCOUNT_NUMBER);
        }

        Optional<BankAccount> optionalSourceAccount = bankAccountRepo.findByBankAccountNumber(bankAccountTransferMoneyRequest.getSourceBankAccountNumber());
        Optional<BankAccount> optionalDestinationAccount = bankAccountRepo.findByBankAccountNumber(bankAccountTransferMoneyRequest.getDestinationBankAccountNumber());

        if (!optionalSourceAccount.isPresent()) {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_NOT_FOUND, "Source account not found");
        }

        if (!optionalDestinationAccount.isPresent()) {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_NOT_FOUND, "Destination account not found");
        }

        BankAccount sourceAccount = optionalSourceAccount.get();
        BankAccount destinationAccount = optionalDestinationAccount.get();
        BigDecimal sourceCurrentBalance = sourceAccount.getAmount();
        BigDecimal transferAmount = bankAccountTransferMoneyRequest.getMoney();

        if (sourceCurrentBalance.compareTo(transferAmount) < 0) {
            throw new BankAccountException(BankAccountExceptionCodes.BANK_ACCOUNT_INSUFFICIENT_BALANCE);
        }

        sourceAccount.setAmount(sourceCurrentBalance.subtract(transferAmount));
        destinationAccount.setAmount(destinationAccount.getAmount().add(transferAmount));

        bankAccountRepo.save(sourceAccount);
        bankAccountRepo.save(destinationAccount);

        BankAccountTransaction transaction = new BankAccountTransaction();
        transaction.setSourceAccountNumber(sourceAccount.getBankAccountNumber());
        transaction.setDestinationAccountNumber(destinationAccount.getBankAccountNumber());
        transaction.setAmount(transferAmount);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransactionTime(LocalDateTime.now());
        bankTransactionRepo.save(transaction);

        sourceCurrentBalance = bankAccountRepo.findByBankAccountNumber(sourceAccount.getBankAccountNumber()).get().getAmount();

        BankAccountMoneyResponse response = BankAccountMoneyResponse.builder()
                .sourceAccountNumber(sourceAccount.getBankAccountNumber())
                .destinationAccountNumber(destinationAccount.getBankAccountNumber())
                .currentBalance(sourceCurrentBalance)  // Return the exact balance from the table
                .transactionTime(LocalDateTime.now())
                .transactionType(TransactionType.TRANSFER)
                .message("Amount transferred successfully.")
                .build();

        return response;
    }

}

/*

{
  "userName": "Ravindra",
  "age": 26,
  "userMobileNumber": {
    "countryCode": "+115",
    "number": "996129766"
  },
  "emailAddress": "ravindra@gmail.com",
  "password": "iamHere@12345",
  "confirmPassword": "iamHere@12345",
  "address": "Tirupati",
  "pincode": "517101",
  "bankAccountType": "SAVINGS"
}


{
  "bankAccountNumber": "217036670878311",
  "userName": "Ravinder",
  "age": 28,
  "userMobileNumber": {
    "countryCode": "+115",
    "number": "996129768"
  },
  "emailAddress": "ravindra8@gmail.com",
  "password": "iamHere@123458",
  "confirmPassword": "iamHere@123458",
  "address": "Tirupati8",
  "pincode": "517108"
}

 */