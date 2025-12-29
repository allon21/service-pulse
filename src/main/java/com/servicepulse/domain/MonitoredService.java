package com.servicepulse.domain;

import com.servicepulse.persistence.entity.MonitoredServiceEntity;

import java.time.Instant;

public class MonitoredService {

    private final Long id;
    private final String name;
    private final String url;
    private final boolean enabled;
    private final Instant createdAt;

    public MonitoredService(
            Long id,
            String name,
            String url,
            boolean enabled,
            Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.enabled = enabled;
        this.createdAt = createdAt;
    }

    public static MonitoredService fromEntity(MonitoredServiceEntity entity) {
        return new MonitoredService(
                entity.getId(),
                entity.getName(),
                entity.getUrl(),
                entity.isEnabled(),
                entity.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
