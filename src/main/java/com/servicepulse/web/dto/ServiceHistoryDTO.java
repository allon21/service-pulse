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
    private final String errorMessage;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public ServiceHistoryDTO(
            ServiceStatus status,
            Long latencyMs,
            Instant checkedAt,
            String errorMessage
    ) {
        this.status = status;
        this.latencyMs = latencyMs != null ? latencyMs : -1;
        this.checkedAt = checkedAt;
        this.errorMessage = errorMessage;

        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(checkedAt, ZoneId.systemDefault());
        this.formattedTime = localDateTime.format(TIME_FORMATTER);
    }

    // Getters
    public ServiceStatus getStatus() {
        return status;
    }

    public long getLatencyMs() {
        return latencyMs;
    }

    public Instant getCheckedAt() {
        return checkedAt;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // Для отображения в шаблоне
    public String getStatusColor() {
        return switch (status) {
            case UP -> "success";
            case DEGRADED -> "warning";
            case DOWN -> "danger";
        };
    }

    public String getLatencyColor() {
        if (latencyMs < 0) return "secondary";
        if (latencyMs < 1000) return "success";
        if (latencyMs < 2000) return "warning";
        return "danger";
    }
}
