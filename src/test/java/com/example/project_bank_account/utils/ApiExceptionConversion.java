package com.example.project_bank_account.utils;

import com.example.project_bank_account.exception.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ApiExceptionConversion {

    public static ApiException convertToApiException(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = null;
        try {
            jsonMap = objectMapper.readValue(responseBody, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Extract values from the map
        String code = (String) jsonMap.get("code");
        String description = (String) jsonMap.get("description");
        List<String> message = null;
        Instant timestamp = Instant.parse((String) jsonMap.get("timestamp"));

        return ApiException.builder()
                .code(code)
                .description(description)
                .message(message)
                .timestamp(timestamp)
                .build();
    }
}