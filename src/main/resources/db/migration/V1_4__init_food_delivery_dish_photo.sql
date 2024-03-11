CREATE TABLE "dish_photo" (
  "id"      serial          not null,
  "name"    varchar(64)     not null,
  "url"     varchar(128)    unique not null,
  PRIMARY KEY ("id")
);