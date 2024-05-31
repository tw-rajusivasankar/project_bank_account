package com.example.project_bank_account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleInvalidArgumentException(MethodArgumentNotValidException exception) {

        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        ApiException response = ApiException.builder()
                .code(BankAccountExceptionCodes.INVALID_PARAMS_OR_REQUEST_BODY.getCode())
                .description(BankAccountExceptionCodes.INVALID_PARAMS_OR_REQUEST_BODY.getDescription())
                .message(errorMap.entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue())
                        .collect(Collectors.toList()))
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiException> handleIllegalArgumentException(IllegalArgumentException exception) {
        ApiException response = ApiException.builder()
                .code(BankAccountExceptionCodes.INVALID_PARAMS_OR_REQUEST_BODY.getCode())
                .description(BankAccountExceptionCodes.INVALID_PARAMS_OR_REQUEST_BODY.getDescription())
                .message(List.of(exception.getMessage()))
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {BankAccountException.class, Exception.class})
    public ResponseEntity<ApiException> handleBankAccountException(Exception ex) {
        ApiException response = null;
        if(ex instanceof BankAccountException) {
            BankAccountException exception = (BankAccountException) ex;
            String description = exception.getBankAccountExceptionCodes().getDescription();
            if (Objects.nonNull(exception.getPlaceHolders())) {
                for (String placeHolder : exception.getPlaceHolders()) {
                    description = description.replaceFirst("%s", placeHolder);
                }
            }

            response = ApiException.builder()
                    .code(exception.getBankAccountExceptionCodes().getCode())
                    .description(description)
                    .timestamp(Instant.now())
                    .build();
        }
        else {
            response = ApiException.builder()
                    .code(BankAccountExceptionCodes.UNKNOWN.getCode())
                    .description(BankAccountExceptionCodes.UNKNOWN.getDescription())
                    .message(List.of(ex.getMessage(), ex.getLocalizedMessage()))
                    .timestamp(Instant.now())
                    .build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}