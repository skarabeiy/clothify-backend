CREATE TABLE custom_clothes_brands
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    auth_user_id INTEGER      NOT NULL,

    CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE
);