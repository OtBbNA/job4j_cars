CREATE TABLE files (
    id      serial      primary key,
    name    varchar     not null,
    path    varchar     not null unique
);