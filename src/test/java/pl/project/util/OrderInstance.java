package pl.project.util;


import pl.project.infrastructure.database.entity.OrderEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static pl.project.util.CustomerInstance.*;
import static pl.project.util.DeliveryServiceInstance.*;

public class OrderInstance {

    private final static OffsetDateTime RECEIVED_DATE_TIME =
            OffsetDateTime.of(2024, 2, 15, 16, 55, 10, 10, ZoneOffset.of("Z"));
    private final static OffsetDateTime COMPLETED_DATE_TIME =
            OffsetDateTime.of(2024, 2, 15, 17, 30, 10, 10, ZoneOffset.of("Z"));

    public static OrderEntity someOrder1(){
        return OrderEntity.builder()
                .orderCode("111")
                .receivedDateTime(RECEIVED_DATE_TIME)
                .completedDateTime(COMPLETED_DATE_TIME)
                .customer(someCustomer1())
                .deliveryService(someDeliveryService1())
                .build();
    }

    public static OrderEntity someOrder2(){
        return OrderEntity.builder()
                .orderCode("222")
                .receivedDateTime(RECEIVED_DATE_TIME)
                .completedDateTime(COMPLETED_DATE_TIME)
                .customer(someCustomer2())
                .deliveryService(someDeliveryService2())
                .build();
    }

    public static OrderEntity someOrder3(){
        return OrderEntity.builder()
                .orderCode("333")
                .receivedDateTime(RECEIVED_DATE_TIME)
                .completedDateTime(COMPLETED_DATE_TIME)
                .customer(someCustomer3())
                .deliveryService(someDeliveryService3())
                .build();
    }

}