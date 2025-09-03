CREATE TABLE outfit_plans
(
    id           SERIAL PRIMARY KEY,
    planned_date DATE    NOT NULL,
    outfit_id    INTEGER NOT NULL,

    CONSTRAINT fk_outfit
        FOREIGN KEY (outfit_id)
            REFERENCES outfits (id)
);