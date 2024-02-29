package pl.project.util.db;

import pl.project.infrastructure.security.db.UserEntity;

public class UserInstance {

    public static UserEntity someUserCustomer1(){
        return UserEntity.builder()
                .userName("some customer1")
                .email("some customer email1")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserCustomer2(){
        return UserEntity.builder()
                .userName("some customer2")
                .email("some customer email2")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserCustomer3(){
        return UserEntity.builder()
                .userName("some customer3")
                .email("some customer email3")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserRestaurantOwner1(){
        return UserEntity.builder()
                .userName("some restaurant owner1")
                .email("some restaurant owner email1")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserRestaurantOwner2(){
        return UserEntity.builder()
                .userName("some restaurant owner2")
                .email("some restaurant owner email2")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserRestaurantOwner3(){
        return UserEntity.builder()
                .userName("some restaurant owner3")
                .email("some restaurant owner email3")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserDeliveryMan1(){
        return UserEntity.builder()
                .userName("some delivery man1")
                .email("some delivery man email1")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserDeliveryMan2(){
        return UserEntity.builder()
                .userName("some delivery man2")
                .email("some delivery man email2")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }

    public static UserEntity someUserDeliveryMan3(){
        return UserEntity.builder()
                .userName("some delivery man3")
                .email("some delivery man email3")
                .password("$2a$12$TaH8x0LMdIUeaNFw14rBRuXvWhJTgbfQy9dDNpsJxdLdzAPnhbP/m")
                .active(true)
                .build();
    }
}
