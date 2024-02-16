package pl.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"opinion", "productEvaluation", "dish"})
@ToString(of = {"opinion", "productEvaluation"})
public class DishOpinion {

    String opinion;
    BigDecimal productEvaluation;
    Dish dish;
    Customer customer;
}
