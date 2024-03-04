package pl.project.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"orderCode","receivedDateTime" ,"completedDateTime"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_code", unique = true, nullable = false)
    private String orderCode;

    @Column(name = "received_date_time", nullable = false)
    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE)
    private OffsetDateTime receivedDateTime;

    @Column(name = "completed_date_time", nullable = true)
    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE)
    private OffsetDateTime completedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_service_id", nullable = false)
    private DeliveryServiceEntity deliveryService;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<DishCompositionEntity> dishCompositions;
}
