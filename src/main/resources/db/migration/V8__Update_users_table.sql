ALTER TABLE users
    DROP COLUMN name,
    DROP COLUMN gender,
    DROP COLUMN date_of_birth;

ALTER TABLE users RENAME TO auth_users;
