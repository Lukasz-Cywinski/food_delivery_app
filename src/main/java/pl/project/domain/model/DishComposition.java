package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"quantity", "dish", "order"})
@ToString(of = {"quantity", "dish"})
public class DishComposition {

    Integer id;
    Integer quantity;
    Dish dish;
    Order order;
}
