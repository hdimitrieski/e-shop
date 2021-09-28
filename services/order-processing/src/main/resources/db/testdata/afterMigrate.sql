delete from order_item;
delete from orders;
delete from payment_method;
delete from buyer;

insert into buyer (id, user_id, name)
values ('1c94903f-59d4-4498-a737-beae5766accd', 'd8222b6b-f59b-4388-9a1c-e5fe9911c4c6', 'Emilija');

insert into payment_method (id, alias, card_holder_name, card_number, card_type, expiration, security_number, buyer_id)
values ('12050c46-6c4b-4f38-91b9-47fd48750dd2', 'payment-alias', 'Emilija Nechkoska', '2453-1234-5543-1234', 'Visa', '2022-03-22', '03020144432',
        '1c94903f-59d4-4498-a737-beae5766accd'
);

insert into orders (id, city, country, state, street, zip_code, buyer_id, description, is_draft, order_date, order_status, payment_method_id)
values ('7913e2b7-c190-470a-837f-ba2c84ab2200', 'Ohrid', 'Macedonia', 'Ohrid', 'Partizanska br.23', '6000',
        '1c94903f-59d4-4498-a737-beae5766accd', 'description...', false, '2021-04-16', 'Shipped', '12050c46-6c4b-4f38-91b9-47fd48750dd2'
);

insert into order_item (id, discount, picture_url, product_id, product_name, unit_price, units, order_id)
values ('dffd044c-6ada-44bb-94c2-8df782d47bb5', 0, null, '4feb96b7-bb3a-4c0a-9763-af16f3f9c361', 'Nike shoes', 23.0, 1, '7913e2b7-c190-470a-837f-ba2c84ab2200');
