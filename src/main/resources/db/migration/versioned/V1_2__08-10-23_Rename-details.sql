ALTER TABLE users
DROP COLUMN detail_id;

ALTER TABLE users
RENAME COLUMN user_details TO detail_id;