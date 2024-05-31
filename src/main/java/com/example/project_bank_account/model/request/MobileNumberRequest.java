package com.example.project_bank_account.model.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MobileNumberRequest {

    @Size(min = 2, max = 5, message = "Country code must be between 2 and 5 characters")
    @Pattern(regexp = "^\\+[0-9]{1,5}$", message = "Country code must start with '+' and consist of 1 to 5 digits only")
    private String countryCode;

    @Size(min = 7, max = 15, message = "Number must be between 7 and 15 characters")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Number must consist of 7-15 digits only")
    private String number;
}