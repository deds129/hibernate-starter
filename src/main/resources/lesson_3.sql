drop table if exists users;

create table users
(
    id bigint primary key,
    username   varchar(128) unique,
    firstname  varchar(128),
    lastname   varchar(128),
    birth_date date,
    role varchar(32)
);

create sequence user_id_seq owned by users.id;

drop sequence user_id_seq;

DROP table users;

create table all_sequence
(
    table_name varchar(32) primary key, 
    pk_value bigint not null
);