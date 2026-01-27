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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public ServiceHistoryResponse getServiceHistory(
            Long serviceId,
            int page,
            int size
    ) {
        MonitoredServiceEntity serviceEntity =
                serviceRepository.findById(serviceId)
                        .orElseThrow();

        Page<HealthCheckResultEntity> pageResult =
                resultRepository.findByService_Id(
                        serviceId,
                        PageRequest.of(page, size)
                );

        List<ServiceHistoryDTO> history =
                pageResult.stream()
                        .map(e -> new ServiceHistoryDTO(
                                e.getStatus(),
                                e.getLatencyMs(),
                                e.getCheckedAt(),
                                e.getErrorMessage()
                        ))
                        .toList();

        return new ServiceHistoryResponse(
                MonitoredService.fromEntity(serviceEntity),
                history,
                pageResult.getTotalElements()
        );
    }

}
