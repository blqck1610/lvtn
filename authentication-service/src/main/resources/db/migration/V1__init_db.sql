create table token
(
    id            uuid    not null primary key,
    access_token  varchar(400),
    is_revoked    boolean not null,
    refresh_token varchar(400),
    type          varchar(20)
        constraint token_type_check
            check ((type)::text = ANY
        ((ARRAY ['BEARER':: character varying, 'ACCESS_TOKEN':: character varying, 'REFRESH_TOKEN':: character varying])::text[])
) ,
    user_id          uuid,
    last_modified_at timestamp(6),
    created_at       timestamp(6)
);
