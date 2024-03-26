package pl.project.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"city", "street", "buildingNumber"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_address")
public class DeliveryAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "building_number", nullable = false)
    private String buildingNumber;

    @Column(name = "street")
    private String street;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryAddress")
    private Set<CustomerEntity> customers;

}
