package com.example.project_bank_account.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConversionConfig implements WebMvcConfigurer {

    @Autowired
    private BankAccountToLoginResponseConverter bankAccountToLoginResponseConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(bankAccountToLoginResponseConverter);
    }
}