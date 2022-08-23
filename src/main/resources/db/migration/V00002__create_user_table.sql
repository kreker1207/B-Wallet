create table user
(
    id         bigint auto_increment,
    country    VARCHAR(35) NOT NULL,
    birth_date VARCHAR(10) NOT NULL,
    email      VARCHAR(40) NOT NULL,
    name       VARCHAR(40) NOT NULL,
    phone      VARCHAR(13) NOT NULL,
    surname    VARCHAR(40) NOT NULL,
    is_active  boolean DEFAULT true,
    constraint USERS_PK
        primary key (id)
);
