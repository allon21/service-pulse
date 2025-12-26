package com.servicepulse.service;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.domain.ServiceStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.Instant;

@Component
public class ServiceChecker {

    private static final long DEGRADED_THRESHOLD_MS = 2000;

    private final WebClient webClient;

    public ServiceChecker(WebClient webClient) {
        this.webClient = webClient;
    }

    public HealthCheckResult check(String serviceName, String url) {
        long start = System.currentTimeMillis();

        try {
            webClient.get()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity()
                    .block(Duration.ofSeconds(5));

            long latency = System.currentTimeMillis() - start;

            ServiceStatus status =
                    latency > DEGRADED_THRESHOLD_MS
                            ? ServiceStatus.DEGRADED
                            : ServiceStatus.UP;

            return new HealthCheckResult(
                    serviceName,
                    status,
                    latency,
                    Instant.now()
            );

        } catch (Exception ex) {
            return new HealthCheckResult(
                    serviceName,
                    ServiceStatus.DOWN,
                    -1,
                    Instant.now()
            );
        }
    }
}
