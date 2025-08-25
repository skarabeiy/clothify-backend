CREATE TABLE outfit_clothes
(
    outfit_id  INTEGER,
    clothes_id INTEGER,
    position_x DOUBLE PRECISION NOT NULL,
    position_y DOUBLE PRECISION NOT NULL,
    scale      DOUBLE PRECISION DEFAULT 1.0,
    rotation   REAL             DEFAULT 0,
    z_index    INTEGER          DEFAULT 0,
    PRIMARY KEY (outfit_id, clothes_id)
);