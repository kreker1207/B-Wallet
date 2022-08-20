create table user
(
    id         bigint auto_increment,
    country    VARCHAR(35),
    birth_date VARCHAR(10),
    email      VARCHAR(40),
    name       VARCHAR(40),
    phone      VARCHAR(13),
    surname    VARCHAR(40),
    is_active boolean,
    constraint USERS_PK
        primary key (id)
);
