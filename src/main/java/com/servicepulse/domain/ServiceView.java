package com.servicepulse.domain;

import java.time.Instant;

public class ServiceView {

    private final Long id;
    private final String name;
    private final String url;
    private final ServiceStatus status;
    private final long latencyMs;
    private final Instant lastCheckedAt;

    private ServiceView(
            Long id,
            String name,
            String url,
            ServiceStatus status,
            long latencyMs,
            Instant lastCheckedAt
    ) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.status = status;
        this.latencyMs = latencyMs;
        this.lastCheckedAt = lastCheckedAt;
    }

    public static ServiceView from(
            MonitoredService service,
            HealthCheckResult result
    ) {
        if (result == null) {
            return new ServiceView(
                    service.getId(),
                    service.getName(),
                    service.getUrl(),
                    null,
                    -1,
                    null
            );
        }

        return new ServiceView(
                service.getId(),
                service.getName(),
                service.getUrl(),
                result.getStatus(),
                result.getLatencyMs(),
                result.getCheckedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public long getLatencyMs() {
        return latencyMs;
    }

    public Instant getLastCheckedAt() {
        return lastCheckedAt;
    }
}
