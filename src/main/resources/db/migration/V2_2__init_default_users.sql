ALTER TABLE customer
ADD COLUMN user_id int,
ADD FOREIGN KEY (user_id) REFERENCES food_delivery_user (id);

ALTER TABLE restaurant_owner
ADD COLUMN user_id int,
ADD FOREIGN KEY (user_id) REFERENCES food_delivery_user (id);

ALTER TABLE delivery_man
ADD COLUMN user_id int,
ADD FOREIGN KEY (user_id) REFERENCES food_delivery_user (id);

insert into food_delivery_user (id, user_name, password, active) values (1, 'default_customer', '$2a$12$uNrvMadWwrpZqVJeMO/JvOaEl3cuUto0qO0rQgQFpziW3EXqmUkRS', true);
insert into food_delivery_user (id, user_name, password, active) values (2, 'default_restaurant_owner', '$2a$12$uNrvMadWwrpZqVJeMO/JvOaEl3cuUto0qO0rQgQFpziW3EXqmUkRS', true);
insert into food_delivery_user (id, user_name, password, active) values (3, 'default_delivery_man', '$2a$12$uNrvMadWwrpZqVJeMO/JvOaEl3cuUto0qO0rQgQFpziW3EXqmUkRS', true);
insert into food_delivery_user (id, user_name, password, active) values (4, 'default_rest_api_user', '$2a$12$uNrvMadWwrpZqVJeMO/JvOaEl3cuUto0qO0rQgQFpziW3EXqmUkRS', true);
insert into food_delivery_user (id, user_name, password, active) values (5, 'default_admin', '$2a$12$uNrvMadWwrpZqVJeMO/JvOaEl3cuUto0qO0rQgQFpziW3EXqmUkRS', true);


insert into delivery_address (id, city, postal_code, street) values (1, 'default city', 'default postal code', 'default street');
insert into customer (id, name, surname, phone_number, email, delivery_address_id, is_active, user_id) values (1, 'default name', 'default surname', 'default phone number', 'default email', 1, true, 1);

insert into restaurant_owner (id, name, surname, phone_number, email, is_active, user_id) values (1, 'default name', 'default surname', 'default phone number', 'default email', true, 2);

insert into delivery_man (id, personal_code, name, surname, phone_number, is_available, is_active, user_id) values (1, 'default name', 'default code', 'default surname', 'default phone number', true, true, 3);

insert into food_delivery_role (id, role) values (1, 'CUSTOMER'), (2, 'RESTAURANT OWNER'), (3, 'DELIVERY MAN'), (4, 'REST API'), (5, 'ADMIN');

insert into food_delivery_user_role (user_id, role_id) values (1, 1);
insert into food_delivery_user_role (user_id, role_id) values (2, 2);
insert into food_delivery_user_role (user_id, role_id) values (3, 3);
insert into food_delivery_user_role (user_id, role_id) values (4, 4);
insert into food_delivery_user_role (user_id, role_id) values (5, 5);

ALTER TABLE customer
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE restaurant_owner
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE delivery_man
ALTER COLUMN user_id SET NOT NULL;
