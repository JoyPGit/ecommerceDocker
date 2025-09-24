DROP TYPE IF EXISTS user_type_enum CASCADE;
CREATE TYPE user_type_enum AS ENUM ('DISTRIBUTOR', 'MANUFACTURER');

DROP TYPE IF EXISTS user_status_enum CASCADE;
CREATE TYPE user_status_enum AS ENUM ('ACTIVE', 'INACTIVE');

DROP TABLE IF EXISTS commerce.users;
CREATE TABLE commerce.users (
    user_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    business_name VARCHAR(256) NOT NULL,
    type user_type_enum NOT NULL,                        -- enum
    address JSON NOT NULL,                               -- json
    business_email VARCHAR(256) NOT NULL,
    business_phone VARCHAR(16) NOT NULL UNIQUE,          -- unique
    business_reg_no VARCHAR(15) NOT NULL,
    tax_id VARCHAR(25) NOT NULL,
    bank_account_details JSON NOT NULL,
    warehouse_tags JSON NOT NULL,
    status user_status_enum NOT NULL DEFAULT 'ACTIVE',
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),       -- timestampTZ
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_user_id ON commerce.users (user_id);   -- users doesn't exist w/o commerce
CREATE INDEX idx_business_email ON commerce.users (business_email);
CREATE INDEX idx_user_status ON commerce.users (status);
CREATE INDEX idx_user_type ON commerce.users (type);