CREATE TABLE auto_post (
    id              serial      primary key,
    description     varchar     unique not null,
    created         timestamp   not null,
    auto_user_id    int         references auto_user(id) not null
);