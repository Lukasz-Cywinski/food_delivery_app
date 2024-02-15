CREATE TABLE "delivery_service" (
  "id"                      serial          not null,
  "delivery_service_code"   varchar(32)     unique not null,
  "received_date_time"      timestamptz     not null,
  "completed_date_time"     timestamptz     ,
  "delivery_man_id"         int             not null,
  PRIMARY KEY ("id"),
  CONSTRAINT "FK_delivery_service.delivery_man_id"
    FOREIGN KEY ("delivery_man_id")
      REFERENCES "delivery_man"("id")
);