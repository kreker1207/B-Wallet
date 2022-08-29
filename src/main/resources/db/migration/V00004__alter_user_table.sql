ALTER TABLE user
    ADD(
    username VARCHAR(35) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roles    VARCHAR(35) NOT NULL DEFAULT 'USER',
    status   VARCHAR(10) DEFAULT 'ACTIVE' NOT NULL
    );
ALTER TABLE user DROP COLUMN is_active;




