create table users
(
    username   varchar(128) not null
        primary key,
    firstname  varchar(128),
    lastname   varchar(128),
    birth_date date,
    role varchar(32),
    info JSONB
);

DROP table users;