ALTER TABLE commerce.users
DROP COLUMN IF EXISTS documents;

ALTER TABLE commerce.users
ADD COLUMN documents JSONB;

DROP TABLE IF EXISTS commerce.user_documents;
CREATE TABLE commerce.user_documents (
    id SERIAL PRIMARY KEY,
    user_id UUID REFERENCES commerce.users(user_id) ON DELETE CASCADE,
    document_data TEXT NOT NULL,
    document_name VARCHAR(256) NOT NULL,
    type VARCHAR(25) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL
);

DROP SEQUENCE IF EXISTS user_documents_seq;
CREATE SEQUENCE user_documents_seq
    START 1
    INCREMENT BY 1
    CACHE 1;
