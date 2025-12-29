package com.servicepulse.api.dto;

public record MonitoredServiceResponse(
        Long id,
        String name,
        String url,
        boolean enabled
) {
}
