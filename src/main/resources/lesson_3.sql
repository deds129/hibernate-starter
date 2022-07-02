create table users
(
    id bigint primary key,
    username   varchar(128) unique,
    firstname  varchar(128),
    lastname   varchar(128),
    birth_date date,
    role varchar(32)
);

DROP table users;