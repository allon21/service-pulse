package com.servicepulse.persistence.repository;

import com.servicepulse.persistence.entity.MonitoredServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MonitoredServiceJpaRepository
        extends JpaRepository<MonitoredServiceEntity, Long> {

    List<MonitoredServiceEntity> findAllByEnabledTrue();

    boolean existsByName(String name);

    Optional<MonitoredServiceEntity> findByName(String name);
}
