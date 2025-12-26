package com.servicepulse.init;

import com.servicepulse.domain.MonitoredService;
import com.servicepulse.domain.ServiceStatus;
import com.servicepulse.repository.ServiceRegistry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer {

    public DataInitializer(ServiceRegistry registry) {

        registry.add(new MonitoredService(
                "Google",
                "https://www.google.com",
                ServiceStatus.UP,
                LocalDateTime.now(),
                -1

        ));

        registry.add(new MonitoredService(
                "GitHub",
                "https://github.com",
                ServiceStatus.UP,
                LocalDateTime.now(),
                -1
        ));

        registry.add(new MonitoredService(
                "Slow test",
                "https://httpstat.us/200?sleep=1500",
                ServiceStatus.UP,
                LocalDateTime.now(),
                -1
        ));
    }
}
