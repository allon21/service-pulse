CREATE TABLE health_check_results (
    id BIGSERIAL PRIMARY KEY,
    service_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    latency_ms BIGINT,
    checked_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_health_check_service
        FOREIGN KEY (service_id)
        REFERENCES monitored_services(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_health_service_checked
    ON health_check_results(service_id, checked_at DESC);
