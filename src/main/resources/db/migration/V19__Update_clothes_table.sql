ALTER TABLE clothes
    DROP COLUMN image,
    ADD COLUMN image_id INTEGER NOT NULL;

ALTER TABLE clothes
    ADD CONSTRAINT fk_image
        FOREIGN KEY (image_id)
            REFERENCES files
            ON DELETE CASCADE;

