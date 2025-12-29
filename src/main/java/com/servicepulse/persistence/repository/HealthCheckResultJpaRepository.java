package com.servicepulse.persistence.repository;

import com.servicepulse.persistence.entity.HealthCheckResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HealthCheckResultJpaRepository
        extends JpaRepository<HealthCheckResultEntity, Long> {

    Optional<HealthCheckResultEntity>
    findFirstByService_IdOrderByCheckedAtDesc(Long serviceId);

    List<HealthCheckResultEntity>
    findByService_IdOrderByCheckedAtAsc(Long serviceId);
}
