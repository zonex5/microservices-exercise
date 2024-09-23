create table libraries
(
    id      serial
        constraint libraries_pk
            primary key,
    name    varchar(150) not null
        constraint libraries_pk2
            unique,
    address varchar(250)
);

create table stock
(
    id         serial
        constraint stock_pk
            primary key,
    id_library integer                 not null
        constraint stock_libraries_id_fk
            references libraries
            on update cascade on delete cascade,
    id_book    integer                 not null,
    quantity   integer       default 0 not null,
    price      numeric(9, 2) default 0 not null,
    constraint stock_pk2
        unique (id_library, id_book)
);

create view library_stock_view(id, id_library, name, address, id_book, quantity, price) as
SELECT DISTINCT s.id,
                l.id AS id_library,
                l.name,
                l.address,
                s.id_book,
                s.quantity,
                s.price
FROM stock s
         JOIN libraries l ON l.id = s.id_library;
