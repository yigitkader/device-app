-- Create custom enum type for device state
DO $$ BEGIN
CREATE TYPE state_type AS ENUM ('AVAILABLE', 'IN_USE', 'INACTIVE');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

-- Create devices table
CREATE TABLE IF NOT EXISTS devices (
                                       id BIGSERIAL PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    state VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT chk_state CHECK (state IN ('AVAILABLE', 'IN_USE', 'INACTIVE')),
    CONSTRAINT chk_name_not_empty CHECK (TRIM(name) <> ''),
    CONSTRAINT chk_brand_not_empty CHECK (TRIM(brand) <> '')
    );

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_devices_brand ON devices(brand);
CREATE INDEX IF NOT EXISTS idx_devices_state ON devices(state);
CREATE INDEX IF NOT EXISTS idx_devices_creation_time ON devices(creation_time DESC);
CREATE INDEX IF NOT EXISTS idx_devices_brand_state ON devices(brand, state);

-- Create a composite index for common query patterns
CREATE INDEX IF NOT EXISTS idx_devices_state_creation ON devices(state, creation_time DESC);

-- Add comments for documentation
COMMENT ON TABLE devices IS 'Stores device information for tracking and management';
COMMENT ON COLUMN devices.id IS 'Primary key, auto-incrementing';
COMMENT ON COLUMN devices.name IS 'Device model name';
COMMENT ON COLUMN devices.brand IS 'Device manufacturer brand';
COMMENT ON COLUMN devices.state IS 'Current state: AVAILABLE, IN_USE, or INACTIVE';
COMMENT ON COLUMN devices.creation_time IS 'Timestamp when device was registered';

-- Grant permissions to application user
GRANT SELECT, INSERT, UPDATE, DELETE ON devices TO device_user;
GRANT USAGE, SELECT ON SEQUENCE devices_id_seq TO device_user;