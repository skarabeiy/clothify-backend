CREATE TABLE images
(
    id      SERIAL PRIMARY KEY,
    public_id VARCHAR(255) NOT NULL UNIQUE,
    url     VARCHAR(255) NOT NULL
);