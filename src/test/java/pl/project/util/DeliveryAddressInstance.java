package pl.project.util;


import pl.project.infrastructure.database.entity.DeliveryAddressEntity;

public class DeliveryAddressInstance {
    public static DeliveryAddressEntity someDeliveryAddress1(){
        return DeliveryAddressEntity.builder()
                .city("city1")
                .postalCode("11-111")
                .street("street1")
                .build();
    }

    public static DeliveryAddressEntity someDeliveryAddress2(){
        return DeliveryAddressEntity.builder()
                .city("city2")
                .postalCode("22-222")
                .street("street2")
                .build();
    }

    public static DeliveryAddressEntity someDeliveryAddress3(){
        return DeliveryAddressEntity.builder()
                .city("city3")
                .postalCode("33-333")
                .street("street3")
                .build();
    }
}
