package com.servicepulse.persistence.repository;

import com.servicepulse.persistence.entity.HealthCheckResultEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthCheckResultJpaRepository
        extends JpaRepository<HealthCheckResultEntity, Long> {

    Optional<HealthCheckResultEntity>
    findFirstByService_IdOrderByCheckedAtDesc(Long serviceId);

    Page<HealthCheckResultEntity>
    findByService_Id(Long serviceId, Pageable pageable);
}
