create table wallet
(
    id         bigint auto_increment,
    name       VARCHAR(35) NOT NULL,
    amount     VARCHAR(35) NOT NULL,
    created_at VARCHAR(10) NOT NULL,
    currency   VARCHAR(10) NOT NULL,
    owner_id   bigint      NOT NULL,
    primary key (id),
    constraint WALLETS_FK
        foreign key (owner_id) references users (id)
);