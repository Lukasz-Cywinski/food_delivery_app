package pl.project.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"personalCode", "name", "surname", "phoneNumber"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_man")
public class DeliveryManEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "personal_code", unique = true, nullable = false)
    private String personalCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phoneNumber", unique = true, nullable = false)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryMan")
    private Set<DeliveryServiceEntity> deliveryServices;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
