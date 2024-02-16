package pl.project.util;


import pl.project.infrastructure.database.entity.DishPhotoEntity;

public class DishPhotoInstance {

    public static DishPhotoEntity someDishPhoto1(){
        return DishPhotoEntity.builder()
                .name("name1")
                .url("url/example1")
                .build();
    }

    public static DishPhotoEntity someDishPhoto2(){
        return DishPhotoEntity.builder()
                .name("name2")
                .url("url/example2")
                .build();
    }

    public static DishPhotoEntity someDishPhoto3(){
        return DishPhotoEntity.builder()
                .name("name3")
                .url("url/example3")
                .build();
    }

}
