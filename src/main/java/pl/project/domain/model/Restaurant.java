package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "restaurantCode")
@ToString(of = {"name", "added"})
public class Restaurant {

    String restaurantCode;
    String name;
    OffsetDateTime added;
    RestaurantOwner restaurantOwner;
    Set<ServedAddress> servedAddresses;
    Set<Dish> dishes;
    boolean isActive;

}
