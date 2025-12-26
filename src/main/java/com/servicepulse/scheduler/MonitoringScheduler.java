package com.servicepulse.scheduler;

import com.servicepulse.domain.MonitoredService;
import com.servicepulse.repository.ServiceRegistry;
import com.servicepulse.service.ServiceChecker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonitoringScheduler {

    private final ServiceRegistry registry;
    private final ServiceChecker checker;

    public MonitoringScheduler(ServiceRegistry registry,
                               ServiceChecker checker) {
        this.registry = registry;
        this.checker = checker;
    }

    @Scheduled(fixedDelay = 10_000)
    public void checkServices() {
        for (MonitoredService service : registry.findAll()) {

            MonitoredService result =
                    checker.check(service.getName(), service.getUrl());

            service.setStatus(result.getStatus());
            service.setLastCheckedAt(result.getLastCheckedAt());
            service.setLatencyMs(result.getLatencyMs());
        }
    }
}
