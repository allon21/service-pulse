package com.servicepulse.domain;

import java.time.Instant;

public class HealthCheckResult {

    private final String serviceName;
    private final ServiceStatus status;
    private final long latencyMs;
    private final Instant checkedAt;

    public HealthCheckResult(
            String serviceName,
            ServiceStatus status,
            long latencyMs,
            Instant checkedAt
    ) {
        this.serviceName = serviceName;
        this.status = status;
        this.latencyMs = latencyMs;
        this.checkedAt = checkedAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public long getLatencyMs() {
        return latencyMs;
    }

    public Instant getCheckedAt() {
        return checkedAt;
    }
}
