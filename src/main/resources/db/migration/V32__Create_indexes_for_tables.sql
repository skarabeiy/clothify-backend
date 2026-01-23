CREATE INDEX idx_clothes_auth_user_id ON clothes (auth_user_id);


CREATE INDEX idx_collections_auth_user_id ON collections (auth_user_id);

CREATE INDEX idx_collection_clothes_collection_id ON collection_clothes (collection_id);


CREATE INDEX idx_clothing_images_auth_user_id ON clothing_images (auth_user_id);

CREATE INDEX idx_links_auth_user_id ON links (auth_user_id);


CREATE INDEX idx_outfits_auth_user_id ON outfits (auth_user_id);

CREATE INDEX idx_outfit_clothes_outfit_id ON outfit_clothes (outfit_id);


CREATE INDEX idx_outfit_plans_user_date ON outfit_plans(auth_user_id, planned_date);