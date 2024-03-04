package pl.project.domain.model;

import jakarta.persistence.Column;
import lombok.*;
import pl.project.infrastructure.security.User;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"name", "surname", "phoneNumber", "email"})
public class Customer {

    Integer id;
    String name;
    String surname;
    String phoneNumber;
    String email;
    DeliveryAddress deliveryAddress;
//    Set<DishOpinion> dishOpinions;
//    Set<Order> orders;
    boolean isActive;
    User user;
}
