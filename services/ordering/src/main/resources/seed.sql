insert into buyer (id, identity_guid, name)
values (1, 'user-id-1', 'Hristijan Dimitrieski');

insert into buyer (id, identity_guid, name)
values (2, 'user-id-2', 'Emilija Nechkoska');

insert into payment_method(id, alias, card_holder_name, card_number, card_type_id, expiration, security_number, buyer_id)
values (1, 'quick', 'Hristijan Dimitrieski', '1234-3212-5324-1234', 1, now(), '283928283', 1);

insert into orders(id, city, country, state, street, zip_code, buyer_id, description, is_draft, order_date, order_status_id, payment_method_id)
values (1, 'Ohrid', 'Macedonia', 'Ohrid', 'Partizanska', '6000', 1, 'Shoes order', false, now(), 1, 1);

insert into order_item (id, discount, picture_url, product_id, product_name, unit_price, units, order_id)
values (1, 20.0, 'nike_shoes.jpg', 9, 'nike shoes', 100, 1, 1);

insert into order_item (id, discount, picture_url, product_id, product_name, unit_price, units, order_id)
values (2, 23.0, 'nike_bag.jpg', 10, 'nike bag', 22, 1, 1);
