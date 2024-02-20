package pl.project.business.dao;

import pl.project.domain.model.Customer;
import pl.project.domain.model.Dish;
import pl.project.domain.model.DishOpinion;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.entity.DishOpinionEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DishOpinionDAO {

    Optional<DishOpinion> createDishOpinion(DishOpinion dishOpinion);

    List<DishOpinion> findDishOpinionsByDish(Dish dish);

    List<DishOpinion> findDishOpinionsByCustomer(Customer customer);

    List<DishOpinion> findDishOpinionsByEvaluationRange(BigDecimal from, BigDecimal to);

    Optional<BigDecimal> getAverageDishEvaluation(Dish dish);
}
