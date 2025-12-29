package com.servicepulse.service;

import com.servicepulse.persistence.entity.MonitoredServiceEntity;
import com.servicepulse.persistence.repository.MonitoredServiceJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MonitoredServiceService {

    private final MonitoredServiceJpaRepository repository;

    public MonitoredServiceService(MonitoredServiceJpaRepository repository) {
        this.repository = repository;
    }

    public MonitoredServiceEntity create(String name, String url) {

        if (repository.existsByName(name)) {

            throw new IllegalArgumentException(
                    "Service with name '" + name + "' already exists"
            );
        }

        MonitoredServiceEntity service =
                new MonitoredServiceEntity(name, url);

        return repository.save(service);
    }

    @Transactional(readOnly = true)
    public List<MonitoredServiceEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<MonitoredServiceEntity> findAllEnabled() {
        return repository.findAllByEnabledTrue();
    }

    public void disable(Long id) {
        MonitoredServiceEntity service = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Service not found: id=" + id
                ));

        service.disable();
    }
}
