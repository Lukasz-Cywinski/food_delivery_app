CREATE TABLE "order" (
  "id"                      serial          not null,
  "order_code"              varchar(32)     unique not null,
  "received_date_time"      timestamptz     not null,
  "completed_date_time"     timestamptz     ,
  "opinion"                 varchar(64)     ,
  "customer_id"             int             not null,
  "delivery_service_id"     int             not null,
  PRIMARY KEY ("id"),
  CONSTRAINT "FK_order.customer_id"
    FOREIGN KEY ("customer_id")
      REFERENCES "customer"("id"),
  CONSTRAINT "FK_order.delivery_service_id"
    FOREIGN KEY ("delivery_service_id")
      REFERENCES "delivery_service"("id")
);