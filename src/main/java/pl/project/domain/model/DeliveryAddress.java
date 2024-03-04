package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"city", "postalCode", "street"})
@ToString(of = {"city", "street", "postalCode"})
public class DeliveryAddress {

    Integer id;
    String city;
    String postalCode;
    String street;
//    Set<Customer> customers;

}
