package pl.project.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.project.infrastructure.security.db.UserEntity;

import java.util.Set;

@Getter
@Setter
@With
@EqualsAndHashCode(of = "id")
@ToString(of = {"name", "surname", "phoneNumber", "email"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_owner")
public class RestaurantOwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantOwner")
    private Set<RestaurantEntity> restaurants;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
