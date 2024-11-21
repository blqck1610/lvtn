create table notification
(
    id                uuid not null
        primary key,
    created_at        timestamp(6),
    is_delete         boolean,
    last_modified     timestamp(6),
    message           varchar(255),
    notification_type varchar(255)
        constraint notification_notification_type_check
            check ((notification_type)::text = ANY
        ((ARRAY ['NOTIFICATION'::character varying, 'ORDER_INFORMATION_CONFIRM'::character varying])::text[])),
    sender            varchar(255),
    to_customer_email varchar(255),
    to_customer_id    uuid
);

alter table notification
    owner to admin;