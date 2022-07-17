DROP table  if exists users;
DROP table  if exists company;
DROP table  if exists profile;

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


CREATE TABLE profile
(
    user_id BIGINT PRIMARY KEY REFERENCES users (id),
    street VARCHAR(128),
    language CHAR(2)
);



