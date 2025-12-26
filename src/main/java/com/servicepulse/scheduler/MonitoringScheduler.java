package com.servicepulse.scheduler;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.repository.HealthCheckResultRepository;
import com.servicepulse.service.MonitoredServiceRegistry;
import com.servicepulse.service.ServiceChecker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonitoringScheduler {

    private final MonitoredServiceRegistry registry;
    private final ServiceChecker checker;
    private final HealthCheckResultRepository repository;

    public MonitoringScheduler(
            MonitoredServiceRegistry registry,
            ServiceChecker checker,
            HealthCheckResultRepository repository
    ) {
        this.registry = registry;
        this.checker = checker;
        this.repository = repository;
    }

    @Scheduled(fixedDelay = 10_000)
    public void checkAll() {
        registry.getAll().forEach(service -> {
            HealthCheckResult result =
                    checker.check(service.getName(), service.getUrl());
            repository.save(result);
        });
    }
}

