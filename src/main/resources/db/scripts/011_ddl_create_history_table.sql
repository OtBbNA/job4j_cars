CREATE TABLE history (
    id          serial      primary key,
    startAt     timestamp   not null,
    endAt     timestamp   not null
);