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
INSERT INTO public."user" (id, avatar, creat_at, date_of_birth, email, first_name, is_delete, last_modified, last_name, password, role, username) VALUES ('f5ae173d-c38a-4bfe-a0b3-5adfbc8ac736', null, '2024-10-25 14:37:44.958935', null, 'admin@email.com', null, false, '2024-10-25 14:37:44.958935', null, '$2a$10$hs429./L/XHwZMqlO6nLqehLY54lAxBQjjB7eAWEjnsjDAkliLXEy', 'ADMIN', 'admin');
INSERT INTO public."user" (id, avatar, creat_at, date_of_birth, email, first_name, is_delete, last_modified, last_name, password, role, username) VALUES ('dd229d7d-d00b-4d49-8acc-3539d29c91cd', null, '2024-11-01 09:45:23.683117', null, 'tdnguyen16102002@gmail.com', null, false, '2024-11-01 09:45:23.683117', null, '$2a$10$gNhILPOs7lWU0fsVk1lwiOz9BpMvRB0EyyG9M1CLsDpRn9Mwlqn.W', 'USER', 'user');