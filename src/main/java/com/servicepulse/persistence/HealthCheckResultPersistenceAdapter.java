package com.servicepulse.persistence;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.persistence.entity.MonitoredServiceEntity;
import com.servicepulse.persistence.mapper.HealthCheckResultMapper;
import com.servicepulse.persistence.repository.HealthCheckResultJpaRepository;
import com.servicepulse.persistence.repository.MonitoredServiceJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HealthCheckResultPersistenceAdapter {

    private final HealthCheckResultJpaRepository repository;
    private final MonitoredServiceJpaRepository serviceRepository;

    public HealthCheckResultPersistenceAdapter(
            HealthCheckResultJpaRepository repository,
            MonitoredServiceJpaRepository serviceRepository
    ) {
        this.repository = repository;
        this.serviceRepository = serviceRepository;
    }

    public void save(HealthCheckResult result) {

        MonitoredServiceEntity service = serviceRepository.findById(
                result.getServiceId()
        ).orElseThrow(() ->
                new IllegalArgumentException(
                        "Service not found: " + result.getServiceId()
                )
        );

        repository.save(
                HealthCheckResultMapper.toEntity(result, service)
        );
    }

    public Optional<HealthCheckResult> findLastByServiceId(Long serviceId) {
        return repository
                .findFirstByService_IdOrderByCheckedAtDesc(serviceId)
                .map(HealthCheckResultMapper::toDomain);
    }
}
