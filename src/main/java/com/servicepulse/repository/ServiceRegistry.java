package com.servicepulse.repository;

import com.servicepulse.domain.MonitoredService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ServiceRegistry {

    private final List<MonitoredService> services =
            new CopyOnWriteArrayList<>();

    public List<MonitoredService> findAll() {
        return services;
    }

    public void add(MonitoredService service) {
        services.add(service);
    }
}
