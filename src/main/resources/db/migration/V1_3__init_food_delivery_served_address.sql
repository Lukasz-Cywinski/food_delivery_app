CREATE TABLE "served_address" (
  "id"              serial      not null,
  "city"            varchar(32) not null,
  "street"          varchar(32) not null,
  "restaurant_id"   int         not null,
  PRIMARY KEY ("id"),
  CONSTRAINT "FK_served_address.restaurant_id"
    FOREIGN KEY ("restaurant_id")
      REFERENCES "restaurant"("id")
);