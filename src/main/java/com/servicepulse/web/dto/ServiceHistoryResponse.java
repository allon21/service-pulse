package com.servicepulse.web.dto;

import com.servicepulse.domain.MonitoredService;

import java.util.List;

public class ServiceHistoryResponse {

    private final MonitoredService service;
    private final List<ServiceHistoryDTO> history;
    private final long totalElements;

    public ServiceHistoryResponse(
            MonitoredService service,
            List<ServiceHistoryDTO> history,
            long totalElements
    ) {
        this.service = service;
        this.history = history;
        this.totalElements = totalElements;
    }

    public MonitoredService getService() {
        return service;
    }

    public List<ServiceHistoryDTO> getHistory() {
        return history;
    }

    public long getTotalElements() {
        return totalElements;
    }
}
