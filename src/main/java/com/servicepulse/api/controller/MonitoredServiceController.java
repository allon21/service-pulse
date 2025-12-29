package com.servicepulse.api.controller;

import com.servicepulse.api.dto.CreateMonitoredServiceRequest;
import com.servicepulse.api.dto.MonitoredServiceResponse;
import com.servicepulse.persistence.entity.MonitoredServiceEntity;
import com.servicepulse.service.MonitoredServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class MonitoredServiceController {

    private final MonitoredServiceService service;

    public MonitoredServiceController(MonitoredServiceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonitoredServiceResponse create(
            @RequestBody @Valid CreateMonitoredServiceRequest request
    ) {
        MonitoredServiceEntity entity =
                service.create(request.name(), request.url());

        return toResponse(entity);
    }

    @GetMapping
    public List<MonitoredServiceResponse> findAll() {
        return service.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @PatchMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable Long id) {
        service.disable(id);
    }

    private MonitoredServiceResponse toResponse(
            MonitoredServiceEntity entity
    ) {
        return new MonitoredServiceResponse(
                entity.getId(),
                entity.getName(),
                entity.getUrl(),
                entity.isEnabled()
        );
    }

}
