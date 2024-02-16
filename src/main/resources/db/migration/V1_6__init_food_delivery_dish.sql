CREATE TABLE "dish" (
  "id"                              serial          not null,
  "dish_code"                       varchar(32)     unique not null,
  "name"                            varchar(32)     not null,
  "description"                     varchar(64)     not null,
  "price"                           numeric(6,2)    not null,
  "average_preparation_time_min"    int             not null,
  "restaurant_id"                   int             not null,
  "dish_photo_id"                   int             ,
  "dish_category_id"                int             not null,
  "is_active"                       boolean         not null,
  PRIMARY KEY ("id"),
  CONSTRAINT "FK_dish.dish_category_id"
    FOREIGN KEY ("dish_category_id")
      REFERENCES "dish_category"("id"),
  CONSTRAINT "FK_dish.restaurant_id"
    FOREIGN KEY ("restaurant_id")
      REFERENCES "restaurant"("id")
);