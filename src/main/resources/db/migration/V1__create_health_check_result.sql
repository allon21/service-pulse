CREATE TABLE health_check_results (
    id BIGSERIAL PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    latency_ms BIGINT,
    checked_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_service_name ON health_check_results(service_name);
CREATE INDEX idx_checked_at ON health_check_results(checked_at);
CREATE INDEX idx_service_checked ON health_check_results(service_name, checked_at DESC);