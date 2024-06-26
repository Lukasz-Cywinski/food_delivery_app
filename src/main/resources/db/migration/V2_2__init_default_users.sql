ALTER TABLE customer
ADD COLUMN user_id int,
ADD FOREIGN KEY (user_id) REFERENCES food_delivery_user (id);

ALTER TABLE restaurant_owner
ADD COLUMN user_id int,
ADD FOREIGN KEY (user_id) REFERENCES food_delivery_user (id);

ALTER TABLE delivery_man
ADD COLUMN user_id int,
ADD FOREIGN KEY (user_id) REFERENCES food_delivery_user (id);

insert into food_delivery_user (id, user_name, email, password, active) values (-1, 'default_customer', 'customerEmail@mail.com', '$2a$12$migHtJqTkooFAWGnPGcSkOLMlcSHavEbTUCdQdYtE6NRF/bYWeHZ2', true);
insert into food_delivery_user (id, user_name, email,  password, active) values (-2, 'default_restaurant_owner', 'restaurantOwnerEmail@mail.com', '$2a$12$migHtJqTkooFAWGnPGcSkOLMlcSHavEbTUCdQdYtE6NRF/bYWeHZ2', true);
insert into food_delivery_user (id, user_name, email,  password, active) values (-3, 'default_delivery_man', 'deliveryManEmail@mail.com', '$2a$12$migHtJqTkooFAWGnPGcSkOLMlcSHavEbTUCdQdYtE6NRF/bYWeHZ2', true);
insert into food_delivery_user (id, user_name, email,  password, active) values (-4, 'default_rest_api_user', 'apiUserEmail@mail.com', '$2a$12$migHtJqTkooFAWGnPGcSkOLMlcSHavEbTUCdQdYtE6NRF/bYWeHZ2', true);
insert into food_delivery_user (id, user_name, email,  password, active) values (-5, 'default_admin', 'adminEmail@gmail.com', '$2a$12$migHtJqTkooFAWGnPGcSkOLMlcSHavEbTUCdQdYtE6NRF/bYWeHZ2', true);


insert into delivery_address (id, city, building_number, street) values (-1, 'my_city', 'my_building_number', 'my_street');
insert into customer (id, name, surname, phone_number, email, delivery_address_id, is_active, user_id) values (-1, 'customer_name', 'customer_surname', '+03 030 030 000', 'customerEmail@mail.com', -1, true, -1);

insert into restaurant_owner (id, name, surname, phone_number, email, is_active, user_id) values (-1, 'owner_name', 'owner_surname', '+02 020 020 000', 'restaurantOwnerEmail@mail.com', true, -2);

insert into delivery_man (id, personal_code, name, surname, phone_number, is_available, is_active, user_id) values (-1, 'deliveryM_name', 'deliveryM_code', 'deliveryM_surname', '+01 010 010 000', true, true, -3);

insert into food_delivery_role (id, role) values (1, 'CUSTOMER'), (2, 'RESTAURANT_OWNER'), (3, 'DELIVERY_MAN'), (4, 'REST_API'), (5, 'ADMIN');

insert into food_delivery_user_role (user_id, role_id) values (-1, 1);
insert into food_delivery_user_role (user_id, role_id) values (-2, 2);
insert into food_delivery_user_role (user_id, role_id) values (-3, 3);
insert into food_delivery_user_role (user_id, role_id) values (-4, 4);
insert into food_delivery_user_role (user_id, role_id) values (-5, 5);

ALTER TABLE customer
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE restaurant_owner
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE delivery_man
ALTER COLUMN user_id SET NOT NULL;
