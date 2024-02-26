package pl.project.business.dao;

import org.springframework.data.domain.Pageable;
import pl.project.domain.model.Customer;
import pl.project.domain.model.Dish;
import pl.project.domain.model.DishOpinion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DishOpinionDAO {

    Optional<DishOpinion> createDishOpinion(DishOpinion dishOpinion);

    List<DishOpinion> findDishOpinionsByDish(Dish dish, Pageable pageable);

    List<DishOpinion> findDishOpinionsByCustomer(Customer customer);

    List<DishOpinion> findDishOpinionsByEvaluationRange(BigDecimal from, BigDecimal to, Pageable pageable);

    Optional<BigDecimal> getAverageDishEvaluation(Dish dish);
}
