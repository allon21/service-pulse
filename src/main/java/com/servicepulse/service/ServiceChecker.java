package com.servicepulse.service;

import com.servicepulse.domain.MonitoredService;
import com.servicepulse.domain.ServiceStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ServiceChecker {

    private static final long DEGRADED_THRESHOLD_MS = 2000;

    private final WebClient webClient;

    public ServiceChecker(WebClient webClient) {
        this.webClient = webClient;
    }

    public MonitoredService check(String name, String url) {
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

            return new MonitoredService(
                    name,
                    url,
                    status,
                    LocalDateTime.now(),
                    latency
            );

        } catch (Exception ex) {
            return new MonitoredService(
                    name,
                    url,
                    ServiceStatus.DOWN,
                    LocalDateTime.now(),
                    -1
            );
        }
    }
}
