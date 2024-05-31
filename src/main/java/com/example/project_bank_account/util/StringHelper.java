package com.example.project_bank_account.util;

import java.util.Random;

public class StringHelper {

    public static String generateBankAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        accountNumber.append(random.nextInt(9) + 1);

        for (int i = 0; i < 14; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }

}