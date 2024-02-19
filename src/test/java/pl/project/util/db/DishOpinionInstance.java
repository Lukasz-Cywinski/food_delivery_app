package pl.project.util.db;


import pl.project.infrastructure.database.entity.DishOpinionEntity;

import java.math.BigDecimal;

import static pl.project.util.db.CustomerInstance.*;
import static pl.project.util.db.DishInstance.*;

public class DishOpinionInstance {

    public static DishOpinionEntity someDishOpinion1(){
        return DishOpinionEntity.builder()
                .opinion("opinion1")
                .productEvaluation(BigDecimal.valueOf(1.11))
                .dish(someDish1())
                .customer(someCustomer1())
                .build();
    }

    public static DishOpinionEntity someDishOpinion2(){
        return DishOpinionEntity.builder()
                .opinion("opinion2")
                .productEvaluation(BigDecimal.valueOf(2.22))
                .dish(someDish2())
                .customer(someCustomer2())
                .build();
    }

    public static DishOpinionEntity someDishOpinion3(){
        return DishOpinionEntity.builder()
                .opinion("opinion3")
                .productEvaluation(BigDecimal.valueOf(3.33))
                .dish(someDish3())
                .customer(someCustomer3())
                .build();
    }
}
