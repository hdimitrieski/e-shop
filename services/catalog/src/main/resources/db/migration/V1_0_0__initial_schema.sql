-- Schema: public

create sequence catalog_sequence
    start with 100
    increment by 1
    no minvalue
    no maxvalue
    cache 1;

create table outbox
(
    id              bigint not null,
    content         varchar(255),
    creation_time   timestamp,
    event_id        uuid,
    event_state     varchar(255),
    event_type_name varchar(255),
    times_sent      integer,
    topic           varchar(255)
);

create table catalog_brand
(
    id    bigint       not null,
    brand varchar(100) not null
);

create table catalog_type
(
    id   bigint       not null,
    type varchar(100) not null
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
    catalog_brand_id    bigint,
    catalog_type_id     bigint
);


alter table only outbox
    add constraint outbox_pkey primary key (id);

alter table only catalog_brand
    add constraint catalog_brand_pkey primary key (id);

alter table only catalog_type
    add constraint catalog_type_pkey primary key (id);

alter table only catalog_item
    add constraint catalog_item_pkey primary key (id);


alter table only catalog_item
    add constraint catalog_brand_id_fk foreign key (catalog_brand_id) references catalog_brand (id);

alter table only catalog_item
    add constraint catalog_type_id_fk foreign key (catalog_type_id) references catalog_type (id);
