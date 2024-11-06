create table inventory
(
    id                 uuid not null
        primary key,
    created_at         timestamp(6),
    is_delete          boolean,
    last_modified      timestamp(6),
    product_id         uuid,
    available_quantity int
);

alter table inventory
    owner to admin;