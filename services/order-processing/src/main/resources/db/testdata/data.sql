delete from order_item;
delete from orders;
delete from payment_method;
delete from buyer;

insert into buyer (user_id, name)
values ('d8222b6b-f59b-4388-9a1c-e5fe9911c4c6', 'Emilija');

insert into payment_method (alias, card_holder_name, card_number, card_type_id, expiration, security_number, buyer_id)
values ('payment-alias', 'Emilija Nechkoska', '2453-1234-5543-1234', 2, '2022-03-22', '03020144432',
        (select id from buyer limit 1)
);

insert into orders (city, country, state, street, zip_code, buyer_id, description, is_draft, order_date, order_status_id, payment_method_id)
values ('Ohrid', 'Macedonia', 'Ohrid', 'Partizanska br.23', '6000', (select id from buyer limit 1), 'description...',
        false, '2021-04-16', 5, (select id from payment_method limit 1)
);

insert into order_item (discount, picture_url, product_id, product_name, unit_price, units, order_id)
values (0, null, 9, 'Nike shoes', 23.0, 1, (select id from orders limit 1));
