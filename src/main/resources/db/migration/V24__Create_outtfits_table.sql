CREATE TABLE outfits
(
id                 SERIAL PRIMARY KEY,
name               VARCHAR(255) NOT NULL,
auth_user_id       INTEGER      NOT NULL,
background_color   VARCHAR(30),
outfit_category_id INTEGER      NOT NULL,
season             VARCHAR(10)  NOT NULL,
created_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE,

CONSTRAINT fk_outfit_category
        FOREIGN KEY (outfit_category_id)
            REFERENCES outfit_categories (id)
);