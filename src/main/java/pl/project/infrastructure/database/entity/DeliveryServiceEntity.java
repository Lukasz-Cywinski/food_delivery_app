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
@ToString(of = {"deliveryServiceCode", "receivedDateTime", "completedDateTime"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_service")
public class DeliveryServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "delivery_service_code", nullable = false)
    private String deliveryServiceCode;

    @Column(name = "received_date_time", nullable = false)
    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE)
    private OffsetDateTime receivedDateTime;

    @Column(name = "completed_date_time", nullable = true)
    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE)
    private OffsetDateTime completedDateTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "delivery_man_id")
    private DeliveryManEntity deliveryMan;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryService")
    private Set<OrderEntity> orders;
}
