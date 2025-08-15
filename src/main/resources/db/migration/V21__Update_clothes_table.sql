ALTER TABLE clothes
    DROP COLUMN common_clothes_brand_id,
    DROP COLUMN custom_clothes_brand_id;

DROP TABLE common_clothes_brands;
DROP TABLE custom_clothes_brands;

CREATE INDEX idx_clothes_brands_auth_user_id ON clothes_brands (auth_user_id);

ALTER TABLE clothes
    ADD COLUMN clothes_brand_id INTEGER NOT NULL;

ALTER TABLE clothes
    ADD CONSTRAINT fk_clothes_brand
        FOREIGN KEY (clothes_brand_id)
            REFERENCES clothes_brands
