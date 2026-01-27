package com.servicepulse.service;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.domain.ServiceStatus;
import com.servicepulse.persistence.entity.MonitoredServiceEntity;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
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

            HttpResponse<Void> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.discarding());

            long latency = System.currentTimeMillis() - start;

            int statusCode = response.statusCode();
            ServiceStatus status;

            if (statusCode >= 200 && statusCode < 300) {
                status = latency > DEGRADED_THRESHOLD_MS
                        ? ServiceStatus.DEGRADED
                        : ServiceStatus.UP;
            } else if (statusCode >= 400 && statusCode < 500) {
                status = ServiceStatus.DEGRADED;
            } else {
                status = ServiceStatus.DOWN;
            }

            return new HealthCheckResult(
                    service.getId(),
                    status,
                    latency,
                    Instant.now(),
                    "HTTP " + statusCode
            );

        } catch (HttpTimeoutException ex) {
            return down(service, "Timeout");
        } catch (UnknownHostException ex) {
            return down(service, "Unknown host");
        } catch (ConnectException ex) {
            return down(service, "Connection refused");
        } catch (Exception ex) {
            return down(service, ex.getClass().getSimpleName());
        }
    }

    private HealthCheckResult down(MonitoredServiceEntity service, String error) {
        return new HealthCheckResult(
                service.getId(),
                ServiceStatus.DOWN,
                null,
                Instant.now(),
                error
        );
    }
}
