package pl.project.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"name", "added"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "restaurant_code", unique = true, nullable = false)
    private String restaurantCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "added", nullable = false)
    private OffsetDateTime added;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_owner_id", nullable = false)
    private RestaurantOwnerEntity restaurantOwner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<ServedAddressEntity> servedAddresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<DishEntity> dishes;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}
