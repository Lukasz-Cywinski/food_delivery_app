CREATE TABLE "customer" (
  "id"                  serial          not null,
  "name"                varchar(32)     not null,
  "surname"             varchar(32)     not null,
  "phone_number"        varchar(32)     unique not null,
  "email"               varchar(32)     unique not null,
  "delivery_address_id" int             not null,
  "is_active"           boolean         not null,
  PRIMARY KEY ("id"),
  CONSTRAINT "FK_customer.delivery_address_id"
    FOREIGN KEY ("delivery_address_id")
      REFERENCES "delivery_address"("id")
);