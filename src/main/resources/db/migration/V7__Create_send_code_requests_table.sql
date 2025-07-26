CREATE TABLE send_code_requests
(
    id              SERIAL PRIMARY KEY,
    email           VARCHAR(255) NOT NULL UNIQUE,
    requests_number INTEGER      NOT NULL
);