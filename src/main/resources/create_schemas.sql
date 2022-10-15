DROP table  if exists users_chat;
DROP table  if exists profile;
DROP table  if exists users;
DROP table  if exists company_locale;
DROP table  if exists company;
DROP table  if exists chat;
DROP table  if exists all_sequence;
drop sequence if exists users_id_seq cascade;

CREATE TABLE company
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY ,
    firstname VARCHAR(128) ,
    lastname VARCHAR(128)  ,
    birth_date DATE ,
    username VARCHAR(128) UNIQUE ,
    role VARCHAR(32),
    info JSONB,
    company_id INT REFERENCES company (id)
);

CREATE TABLE company_locale
(
    company_id INT NOT NULL REFERENCES company (id),
    lang CHAR(2) NOT NULL ,
    description VARCHAR(128) NOT NULL ,
    PRIMARY KEY (company_id, lang)
);

CREATE TABLE chat
(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE users_chat
(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT REFERENCES users (id),
    chat_id BIGINT REFERENCES chat (id),
    created_at TIMESTAMP NOT NULL ,
    created_by VARCHAR(128) NOT NULL
);

CREATE TABLE profile
(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users (id),
    street VARCHAR(128),
    language CHAR(2)
);

create table all_sequence
(
    table_name VARCHAR(32) PRIMARY KEY ,
    pk_value BIGINT NOT NULL
);

-- create sequence users_id_seq
--     owned by users.id;

