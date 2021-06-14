-- Schema: public

create sequence eshop_sequence
    start with 100
    increment by 1
    no minvalue
    no maxvalue
    cache 1;


create table client_request
(
    id   uuid not null,
    name varchar(255),
    time timestamp
);

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

create table buyer
(
    id            bigint       default nextval('eshop_sequence') not null,
    user_id       varchar(200) not null,
    name          varchar(255)
);

create table payment_method
(
    id               bigint       default nextval('eshop_sequence') not null,
    alias            varchar(200) not null,
    card_holder_name varchar(200) not null,
    card_number      varchar(25)  not null,
    card_type_id     integer      not null,
    expiration       date,
    security_number  varchar(200) not null,
    buyer_id         bigint       not null
);

create table orders
(
    id                bigint    default nextval('eshop_sequence') not null,
    city              varchar(255),
    country           varchar(255),
    state             varchar(255),
    street            varchar(255),
    zip_code          varchar(255),
    buyer_id          bigint,
    description       varchar(255),
    is_draft          boolean   not null,
    order_date        timestamp not null,
    order_status_id   integer   not null,
    payment_method_id bigint
);

create table order_item
(
    id           bigint           default nextval('eshop_sequence') not null,
    discount     double precision,
    picture_url  varchar(255),
    product_id   bigint           not null,
    product_name varchar(255)     not null,
    unit_price   double precision not null,
    units        integer          not null,
    order_id     bigint           not null
);


alter table only client_request
    add constraint client_request_pkey primary key (id);

alter table only outbox
    add constraint outbox_pkey primary key (id);

alter table only buyer
    add constraint buyer_pkey primary key (id);

alter table only buyer
    add constraint uk_user_id unique (user_id);

alter table only payment_method
    add constraint payment_method_pkey primary key (id);

alter table only orders
    add constraint orders_pkey primary key (id);

alter table only order_item
    add constraint order_item_pkey primary key (id);


alter table only payment_method
    add constraint buyer_id_fk foreign key (buyer_id) references buyer (id);

alter table only order_item
    add constraint order_id_fk foreign key (order_id) references orders (id);
