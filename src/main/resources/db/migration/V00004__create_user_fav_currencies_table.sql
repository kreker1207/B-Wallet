create table user_fav_currencies
(
    user_id        bigint auto_increment,
    fav_currencies VARCHAR(3) NOT NULL,
    KEY `CURRENCIES_IX` (`user_id`)
);