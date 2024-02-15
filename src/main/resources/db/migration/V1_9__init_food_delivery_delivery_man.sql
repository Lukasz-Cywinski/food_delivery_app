CREATE TABLE "delivery_man" (
  "id"              serial          not null,
  "personal_code"   varchar(32)     unique not null,
  "name"            varchar(32)     not null,
  "surname"         varchar(32)     not null,
  "phone_number"    varchar(32)     unique not null,
  PRIMARY KEY ("id")
);