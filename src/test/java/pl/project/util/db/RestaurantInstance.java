package pl.project.util.db;


import pl.project.infrastructure.database.entity.RestaurantEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static pl.project.util.db.RestaurantOwnerInstance.*;

public class RestaurantInstance {

    private final static OffsetDateTime ADD_DATE_TIME1 =
            OffsetDateTime.of(2024, 2, 15, 16, 30, 10, 10, ZoneOffset.of("Z"));
    private final static OffsetDateTime ADD_DATE_TIME2 =
            OffsetDateTime.of(2024, 2, 10, 16, 30, 10, 10, ZoneOffset.of("Z"));

    private final static OffsetDateTime ADD_DATE_TIME3 =
            OffsetDateTime.of(2024, 2, 5, 16, 30, 10, 10, ZoneOffset.of("Z"));

    public static RestaurantEntity someRestaurant1(){
        return RestaurantEntity.builder()
                .restaurantCode("111")
                .name("name1")
                .added(ADD_DATE_TIME1)
                .restaurantOwner(someRestaurantOwner1())
                .isActive(true)
                .build();
    }

    public static RestaurantEntity someRestaurant2(){
        return RestaurantEntity.builder()
                .restaurantCode("222")
                .name("name2")
                .added(ADD_DATE_TIME2)
                .restaurantOwner(someRestaurantOwner2())
                .isActive(true)
                .build();
    }

    public static RestaurantEntity someRestaurant3(){
        return RestaurantEntity.builder()
                .restaurantCode("333")
                .name("name3")
                .added(ADD_DATE_TIME3)
                .restaurantOwner(someRestaurantOwner3())
                .isActive(true)
                .build();
    }

}
