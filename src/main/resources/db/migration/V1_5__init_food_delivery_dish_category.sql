CREATE TABLE "dish_category" (
  "id"              serial          not null,
  "name"            varchar(32)     not null,
  "description"     varchar(64)     not null,
  PRIMARY KEY ("id")
);