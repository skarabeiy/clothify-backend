CREATE TABLE clothes
(
    id                      SERIAL PRIMARY KEY,
    name                    VARCHAR(255) NOT NULL,
    auth_user_id            INTEGER      NOT NULL,
    image                   VARCHAR(255) NOT NULL,
    background_color        VARCHAR(30),
    clothes_category_id     INTEGER      NOT NULL,
    common_clothes_brand_id INTEGER,
    custom_clothes_brand_id INTEGER,
    color                   VARCHAR(30)  NOT NULL,
    season                  VARCHAR(10)  NOT NULL,
    price                   VARCHAR(255),
    date_of_purchase        TIMESTAMP,

    CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_clothes_category
        FOREIGN KEY (clothes_category_id)
            REFERENCES clothes_categories (id),

    CONSTRAINT fk_common_clothes_brand
        FOREIGN KEY (common_clothes_brand_id)
            REFERENCES common_clothes_brands (id),

    CONSTRAINT fk_custom_clothes_brand
        FOREIGN KEY (custom_clothes_brand_id)
            REFERENCES custom_clothes_brands (id)
);