create table user_fav_currencies
(
    user_id        bigint auto_increment,
    fav_currencies VARCHAR(3) NOT NULL,
    KEY            `FKi1y3odb4cgt14lyikgirqkq9o` (`user_id`)
);