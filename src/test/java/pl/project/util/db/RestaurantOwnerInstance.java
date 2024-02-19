package pl.project.util.db;


import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;

public class RestaurantOwnerInstance {
    public static RestaurantOwnerEntity someRestaurantOwner1(){
        return RestaurantOwnerEntity.builder()
                .name("name1")
                .surname("surname1")
                .phoneNumber("111")
                .email("email1@mail.com")
                .isActive(true)
                .build();
    }

    public static RestaurantOwnerEntity someRestaurantOwner2(){
        return RestaurantOwnerEntity.builder()
                .name("name2")
                .surname("surname2")
                .phoneNumber("222")
                .email("email2@mail.com")
                .isActive(true)
                .build();
    }

    public static RestaurantOwnerEntity someRestaurantOwner3(){
        return RestaurantOwnerEntity.builder()
                .name("name3")
                .surname("surname3")
                .phoneNumber("333")
                .email("email3@mail.com")
                .isActive(true)
                .build();
    }

}
