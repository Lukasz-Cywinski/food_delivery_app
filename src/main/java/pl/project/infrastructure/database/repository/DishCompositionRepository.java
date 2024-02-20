package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishCompositionDAO;
import pl.project.domain.model.DishComposition;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.OrderEntity;
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
    public List<DishComposition> findDishCompositionByOrder(Order order) {
        return dishCompositionJpaRepository.findByOrder(orderEntityMapper.mapToEntity(order)).stream()
                .map(dishCompositionEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<DishComposition> createDishCategory(DishComposition dishComposition) {
        return Optional.ofNullable(dishCompositionEntityMapper.mapFromEntity(
                dishCompositionJpaRepository.save(
                        dishCompositionEntityMapper.mapToEntity(dishComposition)
                )
        ));
    }
}
