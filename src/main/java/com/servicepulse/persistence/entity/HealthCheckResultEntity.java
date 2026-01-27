package com.servicepulse.persistence.entity;

import com.servicepulse.domain.ServiceStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "health_check_results")
public class HealthCheckResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private MonitoredServiceEntity service;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceStatus status;

    @Column(name = "latency_ms")
    private Long latencyMs;

    @Column(name = "checked_at", nullable = false)
    private Instant checkedAt;

    @Column(name = "error_message")
    private String errorMessage;

    public HealthCheckResultEntity() {
    }

    public Long getId() {
        return id;
    }

    public MonitoredServiceEntity getService() {
        return service;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setService(MonitoredServiceEntity service) {
        this.service = service;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public void setLatencyMs(Long latencyMs) {
        this.latencyMs = latencyMs;
    }

    public void setCheckedAt(Instant checkedAt) {
        this.checkedAt = checkedAt;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
