CREATE TABLE "orders" (
  "id"                      serial          not null,
  "order_code"              varchar(32)     unique not null,
  "received_date_time"      timestamptz     not null,
  "completed_date_time"     timestamptz     ,
  "customer_id"             int             not null,
  "delivery_service_id"     int             not null,
  PRIMARY KEY ("id"),
  CONSTRAINT "FK_orders.customer_id"
    FOREIGN KEY ("customer_id")
      REFERENCES "customer"("id"),
  CONSTRAINT "FK_orders.delivery_service_id"
    FOREIGN KEY ("delivery_service_id")
      REFERENCES "delivery_service"("id")
);