package com.example.project_bank_account.model.request;

import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BankAccountUpdateRequest {

    private String bankAccountNumber;

    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z]+(?:\\s+[a-zA-Z]+)*$", message = "Name must contain only alphabets and spaces, between 3 and 30 characters, and no digits or special characters except spaces")
    private String userName;

    private int age;

    @Embedded
    @Valid
    private MobileNumberRequest userMobileNumber;

    private String emailAddress;

    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]+$", message = "Password must have at least one lowercase, one uppercase, one digit, and one special character")
    private String password;

    @Size(min = 8, max = 15, message = "Confirm password must be between 8 and 15 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]+$", message = "Confirm password must have at least one lowercase, one uppercase, one digit, and one special character")
    private String confirmPassword;

    private String address;

    private String pincode;
}