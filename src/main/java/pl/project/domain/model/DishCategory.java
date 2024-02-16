package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"name", "description"})
@ToString(of = {"name", "description"})
public class DishCategory {

    String name;
    String description;
    Set<Dish> dishes;
}
