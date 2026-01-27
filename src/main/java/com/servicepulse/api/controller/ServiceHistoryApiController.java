package com.servicepulse.api.controller;

import com.servicepulse.service.MonitoringService;
import com.servicepulse.web.dto.ServiceHistoryResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class ServiceHistoryApiController {

    private final MonitoringService monitoringService;

    public ServiceHistoryApiController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/{id}/history")
    public ServiceHistoryResponse history(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return monitoringService.getServiceHistory(id, page, size);
    }
}