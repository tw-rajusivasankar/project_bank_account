package com.example.project_bank_account.utils;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

public class HttpRequestSender {
    private static final String BASE_URL = "http://localhost:";
    private static final HttpHeaders DEFAULT_HEADERS = new HttpHeaders();
    private static final TestRestTemplate restTemplate = new TestRestTemplate();

    static {
        DEFAULT_HEADERS.setContentType(MediaType.APPLICATION_JSON);
    }

    public static <T> ResponseEntity<T> sendPostRequest(String url, int port, Object request, ParameterizedTypeReference<T> responseType) {
        HttpEntity<Object> httpEntity = new HttpEntity<>(request, DEFAULT_HEADERS);
        return restTemplate.exchange(BASE_URL + port + url, HttpMethod.POST, httpEntity, responseType);
    }

    public static <T> ResponseEntity<T> sendGetRequest(String url, int port, ParameterizedTypeReference<T> responseType) {
        HttpEntity<Object> httpEntity = new HttpEntity<>(DEFAULT_HEADERS);
        return restTemplate.exchange(BASE_URL + port + url, HttpMethod.GET, httpEntity, responseType);
    }

    public static <T> ResponseEntity<T> sendDeleteRequest(String url, int port, ParameterizedTypeReference<T> responseType) {
        HttpEntity<Object> httpEntity = new HttpEntity<>(DEFAULT_HEADERS);
        return restTemplate.exchange(BASE_URL + port + url, HttpMethod.DELETE, httpEntity, responseType);
    }
}