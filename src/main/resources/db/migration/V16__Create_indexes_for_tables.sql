CREATE INDEX idx_clothes_auth_user_id ON clothes(auth_user_id);

CREATE INDEX idx_custom_clothes_brands_auth_user_id ON custom_clothes_brands(auth_user_id);

CREATE INDEX idx_user_profiles_auth_user_id ON user_profiles(auth_user_id);