package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "deliveryServiceCode")
@ToString(of = {"deliveryServiceCode", "receivedDateTime", "completedDateTime"})
public class DeliveryService {

    Integer id;
    String deliveryServiceCode;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    DeliveryMan deliveryMan;
//    Set<Order> orders;
}
