package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"name", "surname", "phoneNumber", "email"})
public class RestaurantOwner {

    String name;
    String surname;
    String phoneNumber;
    String email;
    Set<Restaurant> restaurants;
    boolean isActive;

}
