CREATE TABLE clothing_images
(
    id                      SERIAL PRIMARY KEY,
    name                    VARCHAR(255) NOT NULL,
    auth_user_id            INTEGER      NOT NULL,
    image_id                INTEGER      NOT NULL,
    background_color        VARCHAR(30),
    clothes_category_id     INTEGER      NOT NULL,
    clothes_brand_id        INTEGER,
    color                   VARCHAR(30)  NOT NULL,
    season                  VARCHAR(10)  NOT NULL,
    created_at              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_image
            FOREIGN KEY (image_id)
                REFERENCES images
                ON DELETE CASCADE,

    CONSTRAINT fk_clothes_category
        FOREIGN KEY (clothes_category_id)
            REFERENCES clothes_categories (id),

    CONSTRAINT fk_clothes_brand
            FOREIGN KEY (clothes_brand_id)
                REFERENCES clothes_brands
);