CREATE TABLE user_profiles
(
    id            SERIAL PRIMARY KEY,
    auth_user_id  INTEGER   NOT NULL,
    name          VARCHAR(255),
    gender        VARCHAR(30),
    date_of_birth DATE,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE
);