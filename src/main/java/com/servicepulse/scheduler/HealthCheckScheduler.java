package com.servicepulse.scheduler;

import com.servicepulse.domain.HealthCheckResult;
import com.servicepulse.persistence.HealthCheckResultPersistenceAdapter;
import com.servicepulse.persistence.entity.MonitoredServiceEntity;
import com.servicepulse.service.MonitoredServiceService;
import com.servicepulse.service.ServiceChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HealthCheckScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(HealthCheckScheduler.class);

    private final MonitoredServiceService monitoredServiceService;
    private final ServiceChecker serviceChecker;
    private final HealthCheckResultPersistenceAdapter resultAdapter;

    public HealthCheckScheduler(
            MonitoredServiceService monitoredServiceService,
            ServiceChecker serviceChecker,
            HealthCheckResultPersistenceAdapter resultAdapter
    ) {
        this.monitoredServiceService = monitoredServiceService;
        this.serviceChecker = serviceChecker;
        this.resultAdapter = resultAdapter;
    }

    @Scheduled(fixedDelay = 30_000)
    public void runHealthChecks() {

        List<MonitoredServiceEntity> services =
                monitoredServiceService.findAllEnabled();

        log.info("Running health checks for {} services", services.size());

        for (MonitoredServiceEntity service : services) {
            HealthCheckResult result = serviceChecker.check(service);
            resultAdapter.save(result);

            log.info(
                    "Service [{}] -> {} ({} ms)",
                    service.getName(),
                    result.getStatus(),
                    result.getLatencyMs()
            );
        }
    }
}
