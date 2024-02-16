package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"city", "street"})
@ToString(of = {"city", "street"})
public class ServedAddress {

    String city;
    String street;
    Restaurant restaurant;


}
