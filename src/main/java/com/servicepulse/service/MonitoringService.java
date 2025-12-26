package com.servicepulse.service;

import com.servicepulse.domain.MonitoredService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringService {

    private final ServiceChecker checker;

    public MonitoringService(ServiceChecker checker) {
        this.checker = checker;
    }

    public List<MonitoredService> getServices() {
        return List.of(
                checker.check("Google", "https://www.google.com"),
                checker.check("GitHub", "https://github.com"),
                checker.check("Slow test", "https://httpstat.us/200?sleep=1500")
        );
    }
}
