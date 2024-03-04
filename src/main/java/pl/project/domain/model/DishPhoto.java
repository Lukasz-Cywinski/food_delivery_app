package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "url")
@ToString(of = {"name", "url"})
public class DishPhoto {

    Integer id;
    String name;
    String url;
    Dish dish;
}
