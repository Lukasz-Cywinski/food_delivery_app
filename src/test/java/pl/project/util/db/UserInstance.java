package pl.project.util.db;

import pl.project.infrastructure.security.UserEntity;

public class UserInstance {

    public static UserEntity someUserCustomer1(){
        return UserEntity.builder()
                .userName("some customer1")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserCustomer2(){
        return UserEntity.builder()
                .userName("some customer2")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserCustomer3(){
        return UserEntity.builder()
                .userName("some customer3")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserRestaurantOwner1(){
        return UserEntity.builder()
                .userName("some restaurant owner1")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserRestaurantOwner2(){
        return UserEntity.builder()
                .userName("some restaurant owner2")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserRestaurantOwner3(){
        return UserEntity.builder()
                .userName("some restaurant owner3")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserDeliveryMan1(){
        return UserEntity.builder()
                .userName("some delivery man1")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserDeliveryMan2(){
        return UserEntity.builder()
                .userName("some delivery man2")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserDeliveryMan3(){
        return UserEntity.builder()
                .userName("some delivery man3")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }
}
