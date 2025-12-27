package com.servicepulse.service;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.domain.MonitoredService;
import com.servicepulse.domain.ServiceView;
import com.servicepulse.persistence.HealthCheckResultPersistenceAdapter;
import com.servicepulse.persistence.entity.HealthCheckResultEntity;
import com.servicepulse.persistence.repository.HealthCheckResultJpaRepository;
import com.servicepulse.web.dto.ServiceHistoryDTO;
import com.servicepulse.web.dto.ServiceHistoryResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonitoringService {

    private final MonitoredServiceRegistry registry;
    private final HealthCheckResultPersistenceAdapter resultAdapter;
    private final HealthCheckResultJpaRepository resultRepository;

    public MonitoringService(
            MonitoredServiceRegistry registry,
            HealthCheckResultPersistenceAdapter resultAdapter,
            HealthCheckResultJpaRepository resultRepository
    ) {
        this.registry = registry;
        this.resultAdapter = resultAdapter;
        this.resultRepository = resultRepository;
    }

    public List<ServiceView> getServicesForDashboard() {
        return registry.getAll().stream()
                .map(service -> {
                    Optional<HealthCheckResult> lastResult =
                            resultAdapter.findLastByServiceName(service.getName());
                    return ServiceView.from(service, lastResult.orElse(null));
                })
                .toList();
    }

    // Получить сервис по имени
    public Optional<MonitoredService> getServiceByName(String name) {
        return registry.getAll().stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Получить историю сервиса (за все время)
    public ServiceHistoryResponse getServiceHistory(String serviceName) {
        Optional<MonitoredService> serviceOpt = getServiceByName(serviceName);

        if (serviceOpt.isEmpty()) {
            throw new IllegalArgumentException("Service not found: " + serviceName);
        }

        MonitoredService service = serviceOpt.get();

        // Используем существующий метод
        List<HealthCheckResultEntity> entities = resultRepository
                .findByServiceNameOrderByCheckedAtDesc(serviceName);

        List<ServiceHistoryDTO> history = entities.stream()
                .map(entity -> new ServiceHistoryDTO(
                        entity.getStatus(),
                        entity.getLatencyMs(),
                        entity.getCheckedAt()
                ))
                .collect(Collectors.toList());

        return new ServiceHistoryResponse(service, history);
    }

    // Получить историю за определенный период
    public ServiceHistoryResponse getServiceHistory(String serviceName, int hours) {
        Optional<MonitoredService> serviceOpt = getServiceByName(serviceName);

        if (serviceOpt.isEmpty()) {
            throw new IllegalArgumentException("Service not found: " + serviceName);
        }

        MonitoredService service = serviceOpt.get();
        Instant fromTime = Instant.now().minus(hours, ChronoUnit.HOURS);

        // Используем метод с фильтрацией по времени
        List<HealthCheckResultEntity> entities = resultRepository
                .findByServiceNameAndCheckedAtAfter(serviceName, fromTime);

        List<ServiceHistoryDTO> history = entities.stream()
                .map(entity -> new ServiceHistoryDTO(
                        entity.getStatus(),
                        entity.getLatencyMs(),
                        entity.getCheckedAt()
                ))
                .collect(Collectors.toList());

        return new ServiceHistoryResponse(service, history);
    }
}