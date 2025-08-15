CREATE TABLE clothes_brands
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    is_custom    BOOLEAN      NOT NULL,
    auth_user_id INTEGER,

    CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE
);