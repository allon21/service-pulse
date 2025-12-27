package com.servicepulse.web.dto;

import com.servicepulse.domain.ServiceStatus;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ServiceHistoryDTO {
    private final ServiceStatus status;
    private final long latencyMs;
    private final Instant checkedAt;
    private final String formattedTime;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public ServiceHistoryDTO(ServiceStatus status, long latencyMs, Instant checkedAt) {
        this.status = status;
        this.latencyMs = latencyMs;
        this.checkedAt = checkedAt;

        LocalDateTime localDateTime = LocalDateTime.ofInstant(checkedAt, ZoneId.systemDefault());
        this.formattedTime = localDateTime.format(TIME_FORMATTER);
    }

    // Getters
    public ServiceStatus getStatus() { return status; }
    public long getLatencyMs() { return latencyMs; }
    public Instant getCheckedAt() { return checkedAt; }
    public String getFormattedTime() { return formattedTime; }

    // Для отображения в шаблоне
    public String getStatusColor() {
        switch (status) {
            case UP: return "success";
            case DEGRADED: return "warning";
            case DOWN: return "danger";
            default: return "secondary";
        }
    }

    public String getLatencyColor() {
        if (latencyMs < 1000) return "success";
        else if (latencyMs < 2000) return "warning";
        else return "danger";
    }
}