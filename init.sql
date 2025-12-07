DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    original_url TEXT NOT NULL,
    short_code TEXT NOT NULL UNIQUE,
    created_at TIMESTAMP
);
