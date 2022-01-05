create table rating
(
    id              uuid not null,
    rating          integer,
    catalog_item_id uuid not null
);
