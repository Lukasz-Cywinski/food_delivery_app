package pl.project.util.db;


import pl.project.infrastructure.database.entity.DeliveryAddressEntity;

public class DeliveryAddressInstance {
    public static DeliveryAddressEntity someDeliveryAddress1(){
        return DeliveryAddressEntity.builder()
                .city("city1")
                .buildingNumber("11-111")
                .street("street1")
                .build();
    }

    public static DeliveryAddressEntity someDeliveryAddress2(){
        return DeliveryAddressEntity.builder()
                .city("city2")
                .buildingNumber("22-222")
                .street("street2")
                .build();
    }

    public static DeliveryAddressEntity someDeliveryAddress3(){
        return DeliveryAddressEntity.builder()
                .city("city3")
                .buildingNumber("33-333")
                .street("street3")
                .build();
    }
}
