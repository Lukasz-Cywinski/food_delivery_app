CREATE TABLE "dish_composition" (
  "id"          serial  not null,
  "quantity"    int     not null,
  "dish_id"     int     not null,
  "order_id"    int     not null,
  PRIMARY KEY ("id"),
  CONSTRAINT "FK_dish_composition.orders_id"
    FOREIGN KEY ("order_id")
      REFERENCES "orders"("id"),
  CONSTRAINT "FK_dish_composition.dish_id"
    FOREIGN KEY ("dish_id")
      REFERENCES "dish"("id")
);