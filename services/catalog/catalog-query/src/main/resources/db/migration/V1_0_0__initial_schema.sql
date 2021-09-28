-- Schema: public

create table brand
(
    id   uuid         not null,
    name varchar(100) not null
);

create table category
(
    id   uuid         not null,
    name varchar(100) not null
);

create table catalog_item
(
    id                  uuid           not null,
    available_stock     integer,
    description         varchar(255),
    name                varchar(50)    not null,
    picture_file_name   varchar(255),
    price               float8 not null,
    brand_id            uuid,
    category_id         uuid
);

alter table only brand
    add constraint brand_pkey primary key (id);

alter table only category
    add constraint category_pkey primary key (id);

alter table only catalog_item
    add constraint catalog_item_pkey primary key (id);

alter table only catalog_item
    add constraint brand_id_fk foreign key (brand_id) references brand (id);

alter table only catalog_item
    add constraint category_id_fk foreign key (category_id) references category (id);
