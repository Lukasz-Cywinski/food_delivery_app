package pl.project.util;


import pl.project.infrastructure.database.entity.DishEntity;

import java.math.BigDecimal;

import static pl.project.util.DishCategoryInstance.*;
import static pl.project.util.DishPhotoInstance.*;
import static pl.project.util.RestaurantInstance.*;

public class DishInstance{

    public static DishEntity someDish1(){
        return DishEntity.builder()
                .dishCode("111")
                .name("name1")
                .description("description1")
                .price(BigDecimal.valueOf(1.00))
                .averagePreparationTimeMin(31)
                .restaurant(someRestaurant1())
                .dishPhoto(someDishPhoto1())
                .dishCategory(someDishCategory1())
                .isActive(true)
                .build();
    }

    public static DishEntity someDish2(){
        return DishEntity.builder()
                .dishCode("222")
                .name("name2")
                .description("description2")
                .price(BigDecimal.valueOf(2.00))
                .averagePreparationTimeMin(32)
                .restaurant(someRestaurant2())
                .dishPhoto(someDishPhoto2())
                .dishCategory(someDishCategory2())
                .isActive(true)
                .build();
    }

    public static DishEntity someDish3(){
        return DishEntity.builder()
                .dishCode("333")
                .name("name3")
                .description("description3")
                .price(BigDecimal.valueOf(3.00))
                .averagePreparationTimeMin(33)
                .restaurant(someRestaurant3())
                .dishPhoto(someDishPhoto3())
                .dishCategory(someDishCategory3())
                .isActive(true)
                .build();
    }

}
