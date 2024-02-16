package pl.project.util;


import pl.project.infrastructure.database.entity.DishCompositionEntity;

import static pl.project.util.DishInstance.*;
import static pl.project.util.OrderInstance.*;

public class DishCompositionInstance {

    public static DishCompositionEntity someDishComposition1(){
        return DishCompositionEntity.builder()
                .quantity(1)
                .dish(someDish1())
                .order(someOrder1())
                .build();
    }

    public static DishCompositionEntity someDishComposition2(){
        return DishCompositionEntity.builder()
                .quantity(2)
                .dish(someDish2())
                .order(someOrder2())
                .build();
    }

    public static DishCompositionEntity someDishComposition3(){
        return DishCompositionEntity.builder()
                .quantity(3)
                .dish(someDish3())
                .order(someOrder3())
                .build();
    }

}
