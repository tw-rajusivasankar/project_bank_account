package com.example.project_bank_account.service;

import com.example.project_bank_account.model.request.*;
import com.example.project_bank_account.model.response.BankAccountBalanceResponse;
import com.example.project_bank_account.model.response.BankAccountLoginResponse;
import com.example.project_bank_account.model.response.BankAccountMoneyResponse;
import com.example.project_bank_account.model.response.BankAccountResponse;

public interface BankAccountService {

    BankAccountResponse bankAccountCreate(BankAccountCreateRequest bankAccountCreateRequest);
    BankAccountResponse bankAccountUpdate(BankAccountUpdateRequest bankAccountUpdateRequest);
    BankAccountLoginResponse bankAccountLogin(BankAccountLoginRequest bankAccountLoginRequest);
    BankAccountBalanceResponse bankAccountCheckBalance(String bankAccountNumber);
    BankAccountMoneyResponse bankAccountAddMoney(BankAccountAddMoneyRequest bankAccountAddMoneyRequest);
    BankAccountMoneyResponse bankAccountWithdrewMoney(BankAccountWithdrawMoneyRequest bankAccountWithdrawMoneyRequest);
    BankAccountMoneyResponse bankAccountTransferMoney(BankAccountTransferMoneyRequest bankAccountTransferMoneyRequest);
}