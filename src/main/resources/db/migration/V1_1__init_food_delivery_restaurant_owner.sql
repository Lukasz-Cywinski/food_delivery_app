CREATE TABLE "restaurant_owner" (
  "id"              serial      not null,
  "name"            varchar(32) not null,
  "surname"         varchar(32) not null,
  "phone_number"    varchar(32) unique not null,
  "email"           varchar(64) unique not null,
  "is_active"       boolean     not null,
  PRIMARY KEY ("id")
);