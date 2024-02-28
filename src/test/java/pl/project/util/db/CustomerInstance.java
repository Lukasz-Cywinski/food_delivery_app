package pl.project.util.db;


import pl.project.infrastructure.database.entity.CustomerEntity;

import static pl.project.util.db.DeliveryAddressInstance.*;
import static pl.project.util.db.UserInstance.*;

public class CustomerInstance {
    public static CustomerEntity someCustomer1(){
        return CustomerEntity.builder()
                .name("name1")
                .surname("surname1")
                .phoneNumber("111")
                .email("email1@mail.com")
                .deliveryAddress(someDeliveryAddress1())
                .isActive(true)
                .user(someUserCustomer1())
                .build();
    }

    public static CustomerEntity someCustomer2(){
        return CustomerEntity.builder()
                .name("name2")
                .surname("surname2")
                .phoneNumber("222")
                .email("email2@mail.com")
                .deliveryAddress(someDeliveryAddress2())
                .isActive(true)
                .user(someUserCustomer2())
                .build();
    }

    public static CustomerEntity someCustomer3(){
        return CustomerEntity.builder()
                .name("name3")
                .surname("surname3")
                .phoneNumber("333")
                .email("email3@mail.com")
                .deliveryAddress(someDeliveryAddress3())
                .isActive(true)
                .user(someUserCustomer3())
                .build();
    }
}
