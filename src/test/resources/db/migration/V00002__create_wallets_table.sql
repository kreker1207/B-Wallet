create table wallet
(
    id         bigint auto_increment,
    name       VARCHAR,
    amount     VARCHAR,
    created_at VARCHAR,
    currency   VARCHAR,
    owner_id   bigint,
);