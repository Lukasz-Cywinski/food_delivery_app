CREATE TABLE food_delivery_user
(
    id          serial        not null,
    user_name   varchar(32)   unique not null,
    password    varchar(128)  not null,
    active      boolean       not null,
    PRIMARY KEY (id)
);

CREATE TABLE food_delivery_role
(
    id      serial      not null,
    role    varchar(20) not null,
    PRIMARY KEY (id)
);

CREATE TABLE food_delivery_user_role
(
    user_id   int      not null,
    role_id   int      not null,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_food_delivery_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES food_delivery_user (id),
    CONSTRAINT fk_food_delivery_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES food_delivery_role (id)
);