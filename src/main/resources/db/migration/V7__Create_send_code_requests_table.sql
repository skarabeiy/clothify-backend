CREATE TABLE send_code_requests
(
    id           SERIAL PRIMARY KEY,
    email        VARCHAR(255) NOT NULL UNIQUE,
    request_time TIMESTAMP    NOT NULL
);