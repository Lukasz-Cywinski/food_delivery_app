package pl.project.domain.model;

import lombok.*;

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
