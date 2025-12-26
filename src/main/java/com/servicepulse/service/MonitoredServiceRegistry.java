package com.servicepulse.service;

import com.servicepulse.domain.MonitoredService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonitoredServiceRegistry {

    private List<MonitoredService> services;

    @PostConstruct
    public void init() {
        services = List.of(
                new MonitoredService(
                        "Google",
                        "https://www.google.com"
                ),
                new MonitoredService(
                        "GitHub",
                        "https://github.com"
                ),
                new MonitoredService(
                        "Slow test",
                        "https://httpstat.us/200?sleep=1500"
                )
        );
    }

    public List<MonitoredService> getAll() {
        return services;
    }
}
