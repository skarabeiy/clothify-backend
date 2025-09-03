CREATE TABLE outfit_plans
(
    id           SERIAL PRIMARY KEY,
    planned_date DATE    NOT NULL,
    outfit_id    INTEGER NOT NULL,
    auth_user_id INTEGER NOT NULL,

    CONSTRAINT fk_outfit
        FOREIGN KEY (outfit_id)
            REFERENCES outfits (id),

    CONSTRAINT fk_auth_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users (id)
            ON DELETE CASCADE
);