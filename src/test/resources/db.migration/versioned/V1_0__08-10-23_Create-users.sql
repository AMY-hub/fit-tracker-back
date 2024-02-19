CREATE TABLE user_details(
                             id bigserial not null primary key,
                             initial_weight numeric(10, 2),
                             target_weight numeric(10, 2),
                             date_of_birth timestamp with time zone,
                             height numeric(10, 2),
                             gender varchar(255)
);

CREATE TABLE user_roles (
                            id bigserial not null primary key,
                            role varchar(255) unique not null
);

INSERT into user_roles (role) VALUES ('USER'), ('ADMIN');

CREATE TABLE users(
                      id        bigserial not null primary key,
                      email     varchar(255) unique not null,
                      firstname varchar(255) not null,
                      lastname  varchar(255),
                      password  varchar(255) not null,
                      role      bigint not null references user_roles(id),
                      created_at timestamp with time zone not null,
                      detail_id  bigint unique references user_details(id) on delete cascade
);