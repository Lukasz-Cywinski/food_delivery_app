package pl.project.util;


import pl.project.infrastructure.database.entity.DeliveryServiceEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static pl.project.util.DeliveryManInstance.*;

public class DeliveryServiceInstance {

    private final static OffsetDateTime RECEIVED_DATE_TIME =
            OffsetDateTime.of(2024, 2, 15, 16, 30, 10, 10, ZoneOffset.of("Z"));
    private final static OffsetDateTime COMPLETED_DATE_TIME =
            OffsetDateTime.of(2024, 2, 15, 17, 30, 10, 10, ZoneOffset.of("Z"));

    public static DeliveryServiceEntity someDeliveryService1(){
        return DeliveryServiceEntity.builder()
                .deliveryServiceCode("111")
                .receivedDateTime(RECEIVED_DATE_TIME)
                .completedDateTime(COMPLETED_DATE_TIME)
                .deliveryMan(someDeliveryMan1())
                .build();
    }

    public static DeliveryServiceEntity someDeliveryService2(){
        return DeliveryServiceEntity.builder()
                .deliveryServiceCode("222")
                .receivedDateTime(RECEIVED_DATE_TIME)
                .completedDateTime(COMPLETED_DATE_TIME)
                .deliveryMan(someDeliveryMan2())
                .build();
    }

    public static DeliveryServiceEntity someDeliveryService3(){
        return DeliveryServiceEntity.builder()
                .deliveryServiceCode("333")
                .receivedDateTime(RECEIVED_DATE_TIME)
                .completedDateTime(COMPLETED_DATE_TIME)
                .deliveryMan(someDeliveryMan3())
                .build();
    }

}
