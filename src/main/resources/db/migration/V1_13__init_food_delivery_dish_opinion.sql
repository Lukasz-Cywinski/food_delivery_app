CREATE TABLE "dish_opinion" (
  "id"                  serial          not null,
  "opinion"             varchar(64)     ,
  "product_evaluation"  numeric(2,1)    check("product_evaluation" between 0 and 5),
  "dish_id"             int             not null,
  "customer_id"         int             not null,
  PRIMARY KEY ("id")
);