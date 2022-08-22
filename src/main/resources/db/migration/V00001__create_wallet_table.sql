create table wallet
(
    id         bigint auto_increment,
    name       VARCHAR(35),
    amount     VARCHAR(35),
    created_at VARCHAR(10),
    currency   VARCHAR(10),
    owner_id   bigint,
    primary key (id),
    constraint WALLETS_FK
        foreign key (owner_id) references users (id)

);