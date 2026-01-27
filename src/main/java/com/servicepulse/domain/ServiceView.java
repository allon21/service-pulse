package com.servicepulse.domain;

public class ServiceView {

    private final MonitoredService service;
    private final HealthCheckResult lastResult;

    private ServiceView(
            MonitoredService service,
            HealthCheckResult lastResult
    ) {
        this.service = service;
        this.lastResult = lastResult;
    }

    public static ServiceView from(
            MonitoredService service,
            HealthCheckResult lastResult
    ) {
        return new ServiceView(service, lastResult);
    }

    //данные сервиса

    public Long getId() {
        return service.getId();
    }

    public String getName() {
        return service.getName();
    }

    public boolean isEnabled() {
        return service.isEnabled();
    }

    public MonitoredService getService() {
        return service;
    }

    //данные последней проверки

    public String getLastStatus() {
        return lastResult != null
                ? lastResult.getStatus().name()
                : "UNKNOWN";
    }

    public Long getLatencyMs() {
        return lastResult != null
                ? lastResult.getLatencyMs()
                : null;
    }
}
