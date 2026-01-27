package com.servicepulse.persistence.mapper;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.persistence.entity.HealthCheckResultEntity;
import com.servicepulse.persistence.entity.MonitoredServiceEntity;

public class HealthCheckResultMapper {

    public static HealthCheckResultEntity toEntity(
            HealthCheckResult domain,
            MonitoredServiceEntity service
    ) {
        HealthCheckResultEntity entity = new HealthCheckResultEntity();
        entity.setService(service);
        entity.setStatus(domain.getStatus());
        entity.setLatencyMs(domain.getLatencyMs());
        entity.setCheckedAt(domain.getCheckedAt());
        entity.setErrorMessage(domain.getErrorMessage());
        return entity;
    }

    public static HealthCheckResult toDomain(
            HealthCheckResultEntity entity
    ) {
        return new HealthCheckResult(
                entity.getService().getId(),
                entity.getStatus(),
                entity.getLatencyMs(),
                entity.getCheckedAt(),
                entity.getErrorMessage()
        );
    }
}
