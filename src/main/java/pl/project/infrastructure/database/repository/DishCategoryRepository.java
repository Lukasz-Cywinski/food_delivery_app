package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishCategoryDAO;
import pl.project.infrastructure.database.repository.jpa.DishCategoryJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishCategoryMapper;

@Repository
@AllArgsConstructor
public class DishCategoryRepository implements DishCategoryDAO {

    private final DishCategoryJpaRepository dishCategoryJpaRepository;
    private final DishCategoryMapper dishCategoryMapper;
}
