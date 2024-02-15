CREATE TABLE "restaurant" (
  "id"                  serial          not null,
  "restaurant_code"     varchar(32)     unique not null,
  "name"                varchar(32)     not null,
  "added"               timestamptz     not null,
  "owner_id"            int             not null,
  PRIMARY KEY ("id"),
  CONSTRAINT "FK_restaurant.owner_id"
    FOREIGN KEY ("owner_id")
      REFERENCES "restaurant_owner"("id")
);