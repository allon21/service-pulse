package com.servicepulse.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class HealthCheckService {

    private final RestClient restClient;

    public HealthCheckService(RestClient restClient) {
        this.restClient = restClient;
    }

    public boolean check(String url) {
        try {
            restClient.get()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity();

            return true;
        } catch (RestClientException ex) {
            return false;
        }
    }
}
