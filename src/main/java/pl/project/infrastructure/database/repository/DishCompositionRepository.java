package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishCompositionDAO;
import pl.project.domain.model.DishComposition;
import pl.project.domain.model.Order;
import pl.project.domain.model.Restaurant;
import pl.project.infrastructure.database.repository.jpa.DishCompositionJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishCompositionEntityMapper;
import pl.project.infrastructure.database.repository.mapper.OrderEntityMapper;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class DishCompositionRepository implements DishCompositionDAO {

    private final DishCompositionJpaRepository dishCompositionJpaRepository;
    private final DishCompositionEntityMapper dishCompositionEntityMapper;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public List<DishComposition> findDishCompositionByOrder(Order order) {
        return dishCompositionJpaRepository.findByOrder(orderEntityMapper.mapToEntity(order)).stream()
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
    public List<Order> getActiveOrdersForRestaurant(Restaurant restaurant) {
        return dishCompositionJpaRepository.findActiveOrdersForRestaurant(
                restaurantEntityMapper.mapToEntity(restaurant)).stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<Order> getRealizedOrdersForRestaurant(Restaurant restaurant) {
        return dishCompositionJpaRepository.findRealizedOrdersForRestaurant(
                restaurantEntityMapper.mapToEntity(restaurant)).stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void deleteDishComposition(DishComposition dishComposition) {
        dishCompositionJpaRepository.delete(
                dishCompositionEntityMapper.mapToEntity(dishComposition));
    }
}
