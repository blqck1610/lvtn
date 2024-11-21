create table "user"
(
    id            uuid    not null
        primary key,
    avatar        varchar(255),
    created_at      timestamp(6),
    date_of_birth date,
    email         varchar(255),
    first_name    varchar(255),
    is_delete     boolean not null,
    last_modified timestamp(6),
    last_name     varchar(255),
    password      varchar(255),
    role          varchar(255)
        constraint user_role_check
            check ((role)::text = ANY
        ((ARRAY ['USER':: character varying, 'ADMIN':: character varying, 'MANAGER':: character varying, 'SUPERVISOR':: character varying])::text[])
) ,
    username      varchar(255)
);

alter table "user"
    owner to admin;

INSERT INTO public."user" (id, avatar, created_at, date_of_birth, email, first_name, is_delete, last_modified, last_name, password, role, username) VALUES ('f5ae173d-c38a-4bfe-a0b3-5adfbc8ac736', null, '2024-10-25 14:37:44.958935', null, 'admin@email.com', null, false, '2024-10-25 14:37:44.958935', null, '$2a$10$hs429./L/XHwZMqlO6nLqehLY54lAxBQjjB7eAWEjnsjDAkliLXEy', 'ADMIN', 'admin');
INSERT INTO public."user" (id, avatar, created_at, date_of_birth, email, first_name, is_delete, last_modified, last_name, password, role, username) VALUES ('dd229d7d-d00b-4d49-8acc-3539d29c91cd', null, '2024-11-01 09:45:23.683117', null, 'tdnguyen16102002@gmail.com', null, false, '2024-11-01 09:45:23.683117', null, '$2a$10$gNhILPOs7lWU0fsVk1lwiOz9BpMvRB0EyyG9M1CLsDpRn9Mwlqn.W', 'USER', 'user');

