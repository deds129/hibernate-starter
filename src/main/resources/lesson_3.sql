DROP table  if exists users_chat;
DROP table  if exists profile;
DROP table  if exists users;
DROP table  if exists company;
DROP table  if exists chat;

create table company
(
    id serial primary key,
    name varchar(64) not null unique
);

create table users
(
    id bigserial primary key,
    username   varchar(128) unique ,
    firstname  varchar(128),
    lastname   varchar(128),
    birth_date date,
    role varchar(32),
    company_id int references company(id)
);

create table chat (
    id BIGSERIAL primary key,
    name varchar(64) not null unique
);

create table users_chat
(
    id BIGSERIAL primary key,
    user_id bigint references users (id),
    chat_id bigint references chat (id),
    created_at timestamp not null,
    created_by varchar(64) not null
);



CREATE TABLE profile
(
    id BIGSERIAL primary key,
    user_id BIGINT not null unique REFERENCES users (id) ,
    street VARCHAR(128),
    language varchar(128)
);