CREATE TABLE apple_client_secret
(
    id            SERIAL PRIMARY KEY,
    client_secret VARCHAR(1000) NOT NULL,
    created_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);