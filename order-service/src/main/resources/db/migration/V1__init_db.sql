create table "order"
(
    id             uuid not null primary key,
    username        varchar,
    order_status   varchar(255),
    note           varchar(255),
    total_amount   float,
    payment_method varchar(255),
    is_delete      boolean,
    created_at     timestamp,
    last_modified  timestamp
);

create table "order_line"
(
    id            uuid not null primary key,
    order_id      uuid,
    product_id    uuid,
    price         float,
    quantity      int,
    is_delete     boolean,
    created_at    timestamp,
    last_modified timestamp
);

alter table "order_line"
    add foreign key ("order_id") references "order" ("id");