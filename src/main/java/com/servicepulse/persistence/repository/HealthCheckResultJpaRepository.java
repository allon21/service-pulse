package com.servicepulse.persistence.repository;

import com.servicepulse.persistence.entity.HealthCheckResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface HealthCheckResultJpaRepository extends JpaRepository<HealthCheckResultEntity, Long> {

    // Найти последний результат для сервиса
    Optional<HealthCheckResultEntity> findFirstByServiceNameOrderByCheckedAtDesc(String serviceName);

    // Найти все результаты для сервиса (для истории)
    List<HealthCheckResultEntity> findByServiceNameOrderByCheckedAtDesc(String serviceName);

    // Найти результаты за последние N часов (этот метод у вас уже есть, но убедитесь, что он правильно написан)
    @Query("SELECT h FROM HealthCheckResultEntity h WHERE h.serviceName = :serviceName " +
            "AND h.checkedAt >= :fromTime ORDER BY h.checkedAt DESC")
    List<HealthCheckResultEntity> findByServiceNameAndCheckedAtAfter(
            @Param("serviceName") String serviceName,
            @Param("fromTime") Instant fromTime
    );

}