create table authors
(
    id            serial
        constraint authors_pk
            primary key,
    name          varchar(150) not null
        constraint authors_pk_2
            unique,
    date_of_birth date         not null,
    date_of_death date
);

create table books
(
    id        serial
        constraint books_pk
            primary key,
    id_author integer      not null
        constraint books_authors_id_fk
            references authors
            on update cascade on delete restrict,
    title     varchar(500) not null,
    edition   integer,
    tags      text[],
    constraint books_pk_2
        unique (id_author, title)
);

comment on column books.edition is 'year of edition';