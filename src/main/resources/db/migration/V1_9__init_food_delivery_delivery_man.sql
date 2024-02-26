CREATE TABLE "delivery_man" (
  "id"              serial          not null,
  "personal_code"   varchar(64)     unique not null,
  "name"            varchar(32)     not null,
  "surname"         varchar(32)     not null,
  "phone_number"    varchar(32)     unique not null,
  "is_available"    boolean         not null,
  "is_active"       boolean         not null,
  PRIMARY KEY ("id")
);