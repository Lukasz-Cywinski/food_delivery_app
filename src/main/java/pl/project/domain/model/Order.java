package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "orderCode")
@ToString(of = {"orderCode","receivedDateTime" ,"completedDateTime"})
public class Order {

    Integer id;
    String orderCode;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    Customer customer;
    DeliveryService deliveryService;
    Set<DishComposition> dishCompositions;
}
