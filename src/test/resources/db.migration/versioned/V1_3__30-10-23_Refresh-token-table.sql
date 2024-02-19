CREATE TABLE refresh_tokens (
        id bigserial not null primary key,
        token varchar(255) unique not null,
        user_id bigint unique references users(id) on delete cascade
);