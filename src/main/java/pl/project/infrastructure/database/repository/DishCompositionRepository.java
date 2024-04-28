package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishCompositionDAO;
import pl.project.domain.model.DishComposition;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.repository.jpa.DishCompositionJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishCompositionEntityMapper;
import pl.project.infrastructure.database.repository.mapper.OrderEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DishCompositionRepository implements DishCompositionDAO {

    private final DishCompositionJpaRepository dishCompositionJpaRepository;
    private final DishCompositionEntityMapper dishCompositionEntityMapper;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public List<DishComposition> findDishCompositionByOrder(String orderCode) {
        return dishCompositionJpaRepository.findByOrder_OrderCode(orderCode).stream()
                .map(dishCompositionEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<DishComposition> createDishComposition(DishComposition dishComposition) {
        return Optional.ofNullable(dishCompositionEntityMapper.mapFromEntity(
                dishCompositionJpaRepository.save(
                        dishCompositionEntityMapper.mapToEntity(dishComposition)
                )
        ));
    }

    @Override
    public List<Order> getActiveOrdersForRestaurant(String restaurantCode) {
        return dishCompositionJpaRepository.findActiveOrdersForRestaurant(
                restaurantCode).stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<Order> getRealizedOrdersForRestaurant(String restaurantCode) {
        return dishCompositionJpaRepository.findRealizedOrdersForRestaurant(
                restaurantCode).stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void deleteDishComposition(DishComposition dishComposition) {
        dishCompositionJpaRepository.delete(
                dishCompositionEntityMapper.mapToEntity(dishComposition));
    }
}
