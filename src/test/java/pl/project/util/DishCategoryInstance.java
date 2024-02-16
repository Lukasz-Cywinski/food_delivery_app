package pl.project.util;


import pl.project.infrastructure.database.entity.DishCategoryEntity;

public class DishCategoryInstance {

    public static DishCategoryEntity someDishCategory1(){
        return DishCategoryEntity.builder()
                .name("name1")
                .description("description1")
                .build();
    }

    public static DishCategoryEntity someDishCategory2(){
        return DishCategoryEntity.builder()
                .name("name2")
                .description("description2")
                .build();
    }

    public static DishCategoryEntity someDishCategory3(){
        return DishCategoryEntity.builder()
                .name("name3")
                .description("description3")
                .build();
    }

}
