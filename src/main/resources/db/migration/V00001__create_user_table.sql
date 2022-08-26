create table users
(
    id         bigint auto_increment,
    username   VARCHAR(35) NOT NULL UNIQUE,
    password   VARCHAR(35) NOT NULL,
    roles      VARCHAR(10) NOT NULL DEFAULT 'USER',
    country    VARCHAR(35) NOT NULL,
    birth_date VARCHAR(10) NOT NULL,
    email      VARCHAR(40) NOT NULL,
    name       VARCHAR(40) NOT NULL,
    phone      VARCHAR(13) NOT NULL,
    surname    VARCHAR(40) NOT NULL,
    status     VARCHAR(10)          DEFAULT 'ACTIVE' NOT NULL,
    constraint USERS_PK
        primary key (id)
);
