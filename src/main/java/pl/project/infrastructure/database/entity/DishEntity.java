package pl.project.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"name", "description", "price", "avergePreparationTimeMin"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dish")
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dish_code", unique = true, nullable = false)
    private String dishCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "averge_preparation_time_min", nullable = false)
    private Integer avergePreparationTimeMin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_photo_id", nullable = false)
    private DishPhotoEntity dishPhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_category_id", nullable = false)
    private DishCategoryEntity dishCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
    private Set<DishCompositionEntity> dishCompositions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
    private Set<DishOpinionEntity> dishOpinions;

}
