CREATE TABLE clothes
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    auth_user_id     INTEGER      NOT NULL,
    image            VARCHAR(255) NOT NULL,
    background_color VARCHAR(30),
    category_id      INTEGER      NOT NULL,
    common_brand_id  INTEGER,
    custom_brand_id  INTEGER,
    color            VARCHAR(30)  NOT NULL,
    season           VARCHAR(10)  NOT NULL,
    price            VARCHAR(255),
    date_of_purchase TIMESTAMP,

    CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id),

    CONSTRAINT fk_common_brand
        FOREIGN KEY (common_brand_id)
            REFERENCES common_brands (id),

    CONSTRAINT fk_custom_brand
        FOREIGN KEY (custom_brand_id)
            REFERENCES custom_brands (id)
);