package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;
import pl.project.infrastructure.security.User;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "personalCode")
@ToString(of = {"personalCode", "name", "surname", "phoneNumber"})
public class DeliveryMan {

    Integer id;
    String personalCode;
    String name;
    String surname;
    String phoneNumber;
//    Set<DeliveryService> deliveryServices;
    boolean isActive;
    boolean isAvailable;
    User user;
}
