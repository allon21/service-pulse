package com.servicepulse.domain;

import java.time.Instant;

public class HealthCheckResult {

    private final Long serviceId;
    private final ServiceStatus status;
    private final Long latencyMs;
    private final Instant checkedAt;

    public HealthCheckResult(
            Long serviceId,
            ServiceStatus status,
            Long latencyMs,
            Instant checkedAt
    ) {
        this.serviceId = serviceId;
        this.status = status;
        this.latencyMs = latencyMs;
        this.checkedAt = checkedAt;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public Long getLatencyMs() {
        return latencyMs;
    }

    public Instant getCheckedAt() {
        return checkedAt;
    }
}

