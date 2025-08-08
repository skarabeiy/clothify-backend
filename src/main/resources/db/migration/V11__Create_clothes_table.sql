CREATE TABLE clothes
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    auth_user_id     INTEGER      NOT NULL,
    image            VARCHAR(255) NOT NULL,
    background_color VARCHAR(30),
    category_id      INTEGER      NOT NULL,
--     brand
    color            VARCHAR(30)  NOT NULL,
    season           VARCHAR(10)  NOT NULL,
    price            VARCHAR(255),
    date_of_purchase TIMESTAMP,

    CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_category
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE
);