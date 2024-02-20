package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishCategoryDAO;
import pl.project.domain.model.DishCategory;
import pl.project.infrastructure.database.repository.jpa.DishCategoryJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishCategoryEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DishCategoryRepository implements DishCategoryDAO {

    private final DishCategoryJpaRepository dishCategoryJpaRepository;
    private final DishCategoryEntityMapper dishCategoryEntityMapper;

    @Override
    public Optional<DishCategory> getDishCategoryByDishCategoryId(Integer dishCategoryID) {
       return dishCategoryJpaRepository.findById(dishCategoryID)
               .map(dishCategoryEntityMapper::mapFromEntity);
    }

    @Override
    public List<DishCategory> getAllDishCategories() {
        return dishCategoryJpaRepository.findAll().stream()
                .map(dishCategoryEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<DishCategory> createDishCategory(DishCategory dishCategory) {
        return Optional.ofNullable(
                dishCategoryEntityMapper.mapFromEntity(
                        dishCategoryJpaRepository.save(
                                dishCategoryEntityMapper.mapToEntity(dishCategory))
                )
        );
    }

    @Override
    public Integer changeDishCategoryDescription(String newDescription, Integer dishCategoryId) {
        return dishCategoryJpaRepository.changeDescription(newDescription, dishCategoryId);
    }
}
