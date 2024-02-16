package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishPhotoDAO;
import pl.project.infrastructure.database.repository.jpa.DishPhotoJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishPhotoMapper;

@Repository
@AllArgsConstructor
public class DishPhotoRepository implements DishPhotoDAO {

    private final DishPhotoJpaRepository dishPhotoJpaRepository;
    private final DishPhotoMapper dishPhotoMapper;
}
