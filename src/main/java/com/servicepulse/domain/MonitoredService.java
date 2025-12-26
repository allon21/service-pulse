package com.servicepulse.domain;

import java.time.LocalDateTime;

public class MonitoredService {

    private String name;
    private String url;
    private ServiceStatus status;
    private LocalDateTime lastCheckedAt;
    private long latencyMs;

    public MonitoredService(
            String name,
            String url,
            ServiceStatus status,
            LocalDateTime lastCheckedAt,
            long latencyMs
    ) {
        this.name = name;
        this.url = url;
        this.status = status;
        this.lastCheckedAt = lastCheckedAt;
        this.latencyMs = latencyMs;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public void setLastCheckedAt(LocalDateTime lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    public void setLatencyMs(long latencyMs) {
        this.latencyMs = latencyMs;
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

    public LocalDateTime getLastCheckedAt() {
        return lastCheckedAt;
    }

    public long getLatencyMs() {
        return latencyMs;
    }
}
