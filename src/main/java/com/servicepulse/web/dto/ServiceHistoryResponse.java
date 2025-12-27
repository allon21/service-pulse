package com.servicepulse.web.dto;

import com.servicepulse.domain.MonitoredService;
import com.servicepulse.domain.ServiceStatus;

import java.util.List;

public class ServiceHistoryResponse {
    private final MonitoredService service;
    private final List<ServiceHistoryDTO> history;
    private final long totalChecks;
    private final long upCount;
    private final long degradedCount;
    private final long downCount;
    private final double availabilityPercentage;

    public ServiceHistoryResponse(MonitoredService service, List<ServiceHistoryDTO> history) {
        this.service = service;
        this.history = history;
        this.totalChecks = history.size();

        long up = history.stream().filter(h -> h.getStatus() == ServiceStatus.UP).count();
        long degraded = history.stream().filter(h -> h.getStatus() == ServiceStatus.DEGRADED).count();
        long down = history.stream().filter(h -> h.getStatus() == ServiceStatus.DOWN).count();

        this.upCount = up;
        this.degradedCount = degraded;
        this.downCount = down;

        this.availabilityPercentage = totalChecks > 0 ?
                ((double) up / totalChecks) * 100 : 0.0;
    }

    // Getters
    public MonitoredService getService() { return service; }
    public List<ServiceHistoryDTO> getHistory() { return history; }
    public long getTotalChecks() { return totalChecks; }
    public long getUpCount() { return upCount; }
    public long getDegradedCount() { return degradedCount; }
    public long getDownCount() { return downCount; }
    public double getAvailabilityPercentage() { return availabilityPercentage; }

    public String getFormattedAvailability() {
        return String.format("%.1f%%", availabilityPercentage);
    }
}