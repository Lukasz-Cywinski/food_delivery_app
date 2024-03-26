package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"city", "buildingNumber", "street"})
@ToString(of = {"city", "street", "buildingNumber"})
public class DeliveryAddress {

    Integer id;
    String city;
    String buildingNumber;
    String street;
//    Set<Customer> customers;

}
