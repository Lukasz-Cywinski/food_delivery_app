package pl.project.util.db;


import pl.project.infrastructure.database.entity.DeliveryManEntity;

import static pl.project.util.db.UserInstance.*;

public class DeliveryManInstance{

    public static DeliveryManEntity someDeliveryMan1(){
        return DeliveryManEntity.builder()
                .personalCode("111")
                .name("name1")
                .surname("surname1")
                .phoneNumber("111")
                .isActive(true)
                .isAvailable(true)
                .user(someUserDeliveryMan1())
                .build();
    }

    public static DeliveryManEntity someDeliveryMan2(){
        return DeliveryManEntity.builder()
                .personalCode("222")
                .name("name2")
                .surname("surname2")
                .phoneNumber("222")
                .isActive(true)
                .isAvailable(true)
                .user(someUserDeliveryMan2())
                .build();
    }

    public static DeliveryManEntity someDeliveryMan3(){
        return DeliveryManEntity.builder()
                .personalCode("333")
                .name("name3")
                .surname("surname3")
                .phoneNumber("333")
                .isActive(true)
                .isAvailable(true)
                .user(someUserDeliveryMan3())
                .build();
    }

}
