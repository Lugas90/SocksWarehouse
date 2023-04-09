--liquibase formatted sql
--changeset EvgeniyL:1
create table socks
(
    accounting      SERIAL PRIMARY KEY,
    color           VARCHAR (255),
    cottonPart      INTEGER,
    quantity        INTEGER
);