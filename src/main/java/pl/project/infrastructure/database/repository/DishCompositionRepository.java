package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishCompositionDAO;
import pl.project.infrastructure.database.repository.jpa.DishCompositionJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishCompositionMapper;

@Repository
@AllArgsConstructor
public class DishCompositionRepository implements DishCompositionDAO {

    private final DishCompositionJpaRepository dishCompositionJpaRepository;
    private final DishCompositionMapper dishCompositionMapper;
}
