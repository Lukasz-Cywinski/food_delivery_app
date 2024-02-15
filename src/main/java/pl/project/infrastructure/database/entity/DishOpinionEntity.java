package pl.project.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"opinion", "productEvaluation"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dish_opinion")
public class DishOpinionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "opinion", nullable = true)
    private String opinion;

    @Column(name = "product_evaluation", nullable = true)
    private BigDecimal productEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", nullable = false)
    private DishEntity dish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
}
