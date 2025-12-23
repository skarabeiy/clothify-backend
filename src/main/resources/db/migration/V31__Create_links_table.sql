CREATE TABLE links
(
    id                      SERIAL PRIMARY KEY,
    name                    VARCHAR(255) NOT NULL,
    auth_user_id            INTEGER      NOT NULL,
    url                     TEXT         NOT NULL,
    clothes_category_id     INTEGER,
    clothes_brand_id        INTEGER,
    color                   VARCHAR(30),
    season                  VARCHAR(10),
    created_at              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_clothes_category
        FOREIGN KEY (clothes_category_id)
            REFERENCES clothes_categories (id),

    CONSTRAINT fk_clothes_brand
            FOREIGN KEY (clothes_brand_id)
                REFERENCES clothes_brands
);