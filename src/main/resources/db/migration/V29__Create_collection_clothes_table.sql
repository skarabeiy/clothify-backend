CREATE TABLE collection_clothes
(
    id            SERIAL PRIMARY KEY,
    collection_id INTEGER NOT NULL,
    clothes_id    INTEGER NOT NULL,
    is_packed     BOOLEAN NOT NULL,

    CONSTRAINT fk_collection
        FOREIGN KEY (collection_id)
            REFERENCES collections (id),

    CONSTRAINT fk_clothes
        FOREIGN KEY (clothes_id)
            REFERENCES clothes (id)
);