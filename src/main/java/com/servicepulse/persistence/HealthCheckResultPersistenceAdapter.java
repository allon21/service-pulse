package com.servicepulse.persistence;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.persistence.mapper.HealthCheckResultMapper;
import com.servicepulse.persistence.repository.HealthCheckResultJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HealthCheckResultPersistenceAdapter {

    private final HealthCheckResultJpaRepository repository;

    public HealthCheckResultPersistenceAdapter(
            HealthCheckResultJpaRepository repository
    ) {
        this.repository = repository;
    }

    public void save(HealthCheckResult result) {
        repository.save(
                HealthCheckResultMapper.toEntity(result)
        );
    }

    public Optional<HealthCheckResult> findLastByServiceName(String serviceName) {
        return repository
                .findFirstByServiceNameOrderByCheckedAtDesc(serviceName)
                .map(HealthCheckResultMapper::toDomain);
    }
}
