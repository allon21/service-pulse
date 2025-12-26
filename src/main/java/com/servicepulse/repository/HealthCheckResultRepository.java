package com.servicepulse.repository;

import com.servicepulse.domain.HealthCheckResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class HealthCheckResultRepository {

    private static final Logger log =
            LoggerFactory.getLogger(HealthCheckResultRepository.class);

    private final Map<String, HealthCheckResult> lastResults =
            new ConcurrentHashMap<>();

    public void save(HealthCheckResult result) {
        lastResults.put(result.getServiceName(), result);

        log.debug(
                "Saved result for [{}]: status={}, latency={}",
                result.getServiceName(),
                result.getStatus(),
                result.getLatencyMs()
        );
    }

    public Optional<HealthCheckResult> findLastByServiceName(
            String serviceName
    ) {
        return Optional.ofNullable(lastResults.get(serviceName));
    }
}
