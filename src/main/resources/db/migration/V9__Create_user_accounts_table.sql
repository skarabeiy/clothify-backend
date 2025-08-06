CREATE TABLE user_accounts
(
    id            SERIAL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    name          VARCHAR(255),
    gender        VARCHAR(30),
    date_of_birth DATE,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
);