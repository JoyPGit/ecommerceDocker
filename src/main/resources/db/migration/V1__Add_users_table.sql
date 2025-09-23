Create table commerce.users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(256) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    soft_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)