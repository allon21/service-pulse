package com.servicepulse.service;
import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.domain.ServiceView;
import com.servicepulse.repository.HealthCheckResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonitoringService {

    private final MonitoredServiceRegistry registry;
    private final HealthCheckResultRepository resultRepository;

    public MonitoringService(
            MonitoredServiceRegistry registry,
            HealthCheckResultRepository resultRepository
    ) {
        this.registry = registry;
        this.resultRepository = resultRepository;
    }

    public List<ServiceView> getServicesForDashboard() {
        return registry.getAll().stream()
                .map(service -> {
                    Optional<HealthCheckResult> lastResult =
                            resultRepository.findLastByServiceName(service.getName());

                    return ServiceView.from(service, lastResult.orElse(null));
                })
                .toList();
    }
}

