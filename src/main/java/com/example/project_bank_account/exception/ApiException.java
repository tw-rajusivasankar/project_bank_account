package com.example.project_bank_account.exception;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ApiException {

    private String code;
    private String description;
    private List<String> message;
    private Instant timestamp;
}