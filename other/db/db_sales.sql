create table sales
(
    id         serial
        constraint sales_pk
            primary key,
    id_library integer                 not null,
    id_book    integer                 not null,
    quantity   integer   default 0     not null,
    sale_date  timestamp default now() not null
);
