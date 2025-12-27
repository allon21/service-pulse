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

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceStatus status;

    @Column(name = "latency_ms")
    private Long latencyMs;

    @Column(name = "checked_at", nullable = false)
    private Instant checkedAt;

    // Constructors
    public HealthCheckResultEntity() {}

    public HealthCheckResultEntity(String serviceName, ServiceStatus status,
                                   Long latencyMs, Instant checkedAt) {
        this.serviceName = serviceName;
        this.status = status;
        this.latencyMs = latencyMs;
        this.checkedAt = checkedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public ServiceStatus getStatus() { return status; }
    public void setStatus(ServiceStatus status) { this.status = status; }

    public Long getLatencyMs() { return latencyMs; }
    public void setLatencyMs(Long latencyMs) { this.latencyMs = latencyMs; }

    public Instant getCheckedAt() { return checkedAt; }
    public void setCheckedAt(Instant checkedAt) { this.checkedAt = checkedAt; }
}