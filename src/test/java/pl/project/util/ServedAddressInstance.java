package pl.project.util;


import pl.project.infrastructure.database.entity.ServedAddressEntity;

import static pl.project.util.RestaurantInstance.*;

public class ServedAddressInstance {

    public static ServedAddressEntity someServedAddress1(){
        return ServedAddressEntity.builder()
                .city("city1")
                .street("street1")
                .restaurant(someRestaurant1())
                .build();
    }

    public static ServedAddressEntity someServedAddress2(){
        return ServedAddressEntity.builder()
                .city("city2")
                .street("street2")
                .restaurant(someRestaurant2())
                .build();
    }

    public static ServedAddressEntity someServedAddress3(){
        return ServedAddressEntity.builder()
                .city("city3")
                .street("street3")
                .restaurant(someRestaurant3())
                .build();
    }

}
