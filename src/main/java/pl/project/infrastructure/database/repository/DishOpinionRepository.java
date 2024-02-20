package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishOpinionDAO;
import pl.project.domain.model.Customer;
import pl.project.domain.model.Dish;
import pl.project.domain.model.DishOpinion;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.repository.jpa.DishOpinionJpaRepository;
import pl.project.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishOpinionEntityMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DishOpinionRepository implements DishOpinionDAO {

    private final DishOpinionJpaRepository dishOpinionJpaRepository;
    private final DishOpinionEntityMapper dishOpinionEntityMapper;
    private final DishEntityMapper dishEntityMapper;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Optional<DishOpinion> createDishOpinion(DishOpinion dishOpinion) {
        return Optional.ofNullable(
                dishOpinionEntityMapper.mapFromEntity(
                        dishOpinionJpaRepository.save(
                                dishOpinionEntityMapper.mapToEntity(dishOpinion)
                        )
                ));
    }

    @Override
    public List<DishOpinion> findDishOpinionsByDish(Dish dish) {

        return dishOpinionJpaRepository.findByDish(
                        dishEntityMapper.mapToEntity(dish)).stream()
                .map(dishOpinionEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<DishOpinion> findDishOpinionsByCustomer(Customer customer) {
        return dishOpinionJpaRepository.findByCustomer(
                        customerEntityMapper.mapToEntity(customer)).stream()
                .map(dishOpinionEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<DishOpinion> findDishOpinionsByEvaluationRange(BigDecimal from, BigDecimal to) {
        return dishOpinionJpaRepository.findByEvaluationRange(from, to).stream()
                .map(dishOpinionEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<BigDecimal> getAverageDishEvaluation(Dish dish) {
        return dishOpinionJpaRepository.getAverageDishEvaluation(
                dishEntityMapper.mapToEntity(dish));
    }
}
