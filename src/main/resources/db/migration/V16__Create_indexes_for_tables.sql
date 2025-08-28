CREATE INDEX idx_clothes_auth_user_id ON clothes (auth_user_id);

CREATE INDEX idx_custom_clothes_brands_auth_user_id ON custom_clothes_brands (auth_user_id);

ALTER TABLE user_profiles
    ADD CONSTRAINT unique_auth_user_id UNIQUE (auth_user_id);