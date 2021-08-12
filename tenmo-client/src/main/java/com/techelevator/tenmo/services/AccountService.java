package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;

public class AccountService {
    private final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();



    public AccountService(String apiURL) {
        API_BASE_URL = apiURL;
    }

    public BigDecimal viewCurrentBalance(String token) {
        BigDecimal balance = null;
        try{
            balance = restTemplate.exchange(API_BASE_URL + "accounts/balance", HttpMethod.GET, makeAuthEntity(token),BigDecimal.class).getBody();
        }
        catch (RestClientResponseException ex) {
            System.out.println("Error"); //new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return balance;
    }

    private HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

}
