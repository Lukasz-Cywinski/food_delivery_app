CREATE TABLE "delivery_address" (
  "id"                      serial          not null,
  "city"                    varchar(32)     not null,
  "building_number"         varchar(32)     not null,
  "street"                  varchar(32)     not null,
  PRIMARY KEY ("id")
);