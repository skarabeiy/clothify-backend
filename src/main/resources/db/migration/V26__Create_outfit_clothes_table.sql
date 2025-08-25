CREATE TABLE outfit_clothes
(
    outfit_id  INTEGER,
    clothes_id INTEGER,
    position_x DOUBLE PRECISION NOT NULL,
    position_y DOUBLE PRECISION NOT NULL,
    scale      DOUBLE PRECISION          DEFAULT 1.0,
    rotation   REAL                      DEFAULT 0,
    z_index    INTEGER                   DEFAULT 0,
    added_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (outfit_id, clothes_id),

    CONSTRAINT fk_outfit
        FOREIGN KEY (outfit_id)
            REFERENCES outfits (id),

    CONSTRAINT fk_clothes
        FOREIGN KEY (clothes_id)
            REFERENCES clothes (id)
);