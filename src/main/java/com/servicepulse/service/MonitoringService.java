package com.servicepulse.service;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.domain.MonitoredService;
import com.servicepulse.domain.ServiceView;
import com.servicepulse.persistence.HealthCheckResultPersistenceAdapter;
import com.servicepulse.persistence.entity.HealthCheckResultEntity;
import com.servicepulse.persistence.entity.MonitoredServiceEntity;
import com.servicepulse.persistence.repository.HealthCheckResultJpaRepository;
import com.servicepulse.persistence.repository.MonitoredServiceJpaRepository;
import com.servicepulse.web.dto.ServiceHistoryDTO;
import com.servicepulse.web.dto.ServiceHistoryResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class MonitoringService {

    private final HealthCheckResultPersistenceAdapter resultAdapter;
    private final HealthCheckResultJpaRepository resultRepository;
    private final MonitoredServiceJpaRepository serviceRepository;

    public MonitoringService(
            HealthCheckResultPersistenceAdapter resultAdapter,
            HealthCheckResultJpaRepository resultRepository,
            MonitoredServiceJpaRepository serviceRepository
    ) {
        this.resultAdapter = resultAdapter;
        this.resultRepository = resultRepository;
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceView> getServicesForDashboard() {
        return serviceRepository.findAll().stream()
                .map(serviceEntity -> {
                    Optional<HealthCheckResult> lastResult =
                            resultAdapter.findLastByServiceId(serviceEntity.getId());

                    return ServiceView.from(
                            MonitoredService.fromEntity(serviceEntity),
                            lastResult.orElse(null)
                    );
                })
                .toList();
    }

    public ServiceHistoryResponse getServiceHistory(Long serviceId) {

        MonitoredServiceEntity serviceEntity = serviceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Service not found: " + serviceId)
                );

        List<HealthCheckResultEntity> entities =
                resultRepository.findByService_IdOrderByCheckedAtAsc(serviceId);

        List<ServiceHistoryDTO> history = entities.stream()
                .map(e -> new ServiceHistoryDTO(
                        e.getStatus(),
                        e.getLatencyMs(),
                        e.getCheckedAt()
                ))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        Collections.reverse(history);

        return new ServiceHistoryResponse(
                MonitoredService.fromEntity(serviceEntity),
                history
        );
    }
}
