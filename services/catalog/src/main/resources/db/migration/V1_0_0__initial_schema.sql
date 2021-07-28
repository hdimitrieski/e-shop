-- Schema: public

create sequence catalog_sequence
    start with 100
    increment by 1
    no minvalue
    no maxvalue
    cache 1;

create table outbox
(
    id              serial not null,
    content         varchar(255),
    creation_time   timestamp,
    event_id        uuid,
    event_state     varchar(255),
    event_type_name varchar(255),
    times_sent      integer,
    topic           varchar(255)
);

create table brand
(
    id   bigint       not null,
    name varchar(100) not null
);

create table category
(
    id   bigint       not null,
    name varchar(100) not null
);

create table catalog_item
(
    id                  bigint         not null,
    available_stock     integer,
    description         varchar(255),
    max_stock_threshold integer,
    name                varchar(50)    not null,
    on_reorder          boolean        not null,
    picture_file_name   varchar(255),
    price               numeric(19, 2) not null,
    restock_threshold   integer,
    brand_id            bigint,
    category_id         bigint
);

alter table only outbox
    add constraint outbox_pkey primary key (id);

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
