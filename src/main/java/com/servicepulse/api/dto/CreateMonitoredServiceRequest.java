package com.servicepulse.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateMonitoredServiceRequest(
        @NotBlank String name,
        @NotBlank String url
) {
}
