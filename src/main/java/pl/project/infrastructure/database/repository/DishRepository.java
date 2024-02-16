package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishDAO;
import pl.project.infrastructure.database.repository.jpa.DishJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishMapper;

@Repository
@AllArgsConstructor
public class DishRepository implements DishDAO {

    private final DishJpaRepository dishJpaRepository;
    private final DishMapper dishMapper;
}
