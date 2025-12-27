package com.servicepulse.persistence.mapper;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.persistence.entity.HealthCheckResultEntity;

public class HealthCheckResultMapper {

    public static HealthCheckResultEntity toEntity(HealthCheckResult domain) {
        if (domain == null) return null;

        return new HealthCheckResultEntity(
                domain.getServiceName(),
                domain.getStatus(),
                domain.getLatencyMs(),
                domain.getCheckedAt()
        );
    }

    public static HealthCheckResult toDomain(HealthCheckResultEntity entity) {
        if (entity == null) return null;

        return new HealthCheckResult(
                entity.getServiceName(),
                entity.getStatus(),
                entity.getLatencyMs(),
                entity.getCheckedAt()
        );
    }
}