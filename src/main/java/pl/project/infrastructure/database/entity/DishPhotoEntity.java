package pl.project.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"name", "url"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dish_photo")
public class DishPhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", unique = true, nullable = false)
    private String url;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "dishPhoto")
    private DishEntity dish;
}
