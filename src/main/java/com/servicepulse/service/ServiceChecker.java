package com.servicepulse.service;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.domain.ServiceStatus;
import com.servicepulse.persistence.entity.MonitoredServiceEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;

@Component
public class ServiceChecker {

    private static final long DEGRADED_THRESHOLD_MS = 2000;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .build();

    public HealthCheckResult check(MonitoredServiceEntity service) {

        long start = System.currentTimeMillis();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(service.getUrl()))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.discarding());

            long latency = System.currentTimeMillis() - start;

            ServiceStatus status =
                    latency > DEGRADED_THRESHOLD_MS
                            ? ServiceStatus.DEGRADED
                            : ServiceStatus.UP;

            return new HealthCheckResult(
                    service.getId(),
                    status,
                    latency,
                    Instant.now()
            );

        } catch (Exception ex) {
            return new HealthCheckResult(
                    service.getId(),
                    ServiceStatus.DOWN,
                    null,
                    Instant.now()
            );
        }
    }
}
