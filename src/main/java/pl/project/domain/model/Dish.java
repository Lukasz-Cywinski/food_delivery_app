package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "dishCode")
@ToString(of = {"name", "description", "price", "averagePreparationTimeMin"})
public class Dish {

    Integer id;
    String dishCode;
    String name;
    String description;
    BigDecimal price;
    Integer averagePreparationTimeMin;
    Restaurant restaurant;
    DishPhoto dishPhoto;
    DishCategory dishCategory;
//    Set<DishComposition> dishCompositions;
//    Set<DishOpinion> dishOpinions;
    boolean isActive;

}
