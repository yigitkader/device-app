-- Create devices table for H2 test database
CREATE TABLE IF NOT EXISTS devices (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    state VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT chk_state CHECK (state IN ('AVAILABLE', 'IN_USE', 'INACTIVE')),
    CONSTRAINT chk_name_not_empty CHECK (TRIM(name) <> ''),
    CONSTRAINT chk_brand_not_empty CHECK (TRIM(brand) <> '')
    );