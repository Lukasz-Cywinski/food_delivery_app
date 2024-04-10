CREATE TABLE "dish_photo" (
  "id"      serial          not null,
  "name"    varchar(256)     not null,
  "url"     varchar(256)    unique not null,
  PRIMARY KEY ("id")
);