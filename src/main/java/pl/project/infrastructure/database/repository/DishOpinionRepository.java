package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishOpinionDAO;
import pl.project.infrastructure.database.repository.jpa.DishOpinionJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishOpinionMapper;

@Repository
@AllArgsConstructor
public class DishOpinionRepository implements DishOpinionDAO {

    private final DishOpinionJpaRepository dishOpinionJpaRepository;
    private final DishOpinionMapper dishOpinionMapper;
}
