package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishPhotoDAO;
import pl.project.domain.model.DishPhoto;
import pl.project.infrastructure.database.repository.jpa.DishPhotoJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishPhotoEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class DishPhotoRepository implements DishPhotoDAO {

    private final DishPhotoJpaRepository dishPhotoJpaRepository;
    private final DishPhotoEntityMapper dishPhotoEntityMapper;

    @Override
    public Optional<DishPhoto> createDishPhoto(DishPhoto dishPhoto) {
        return Optional.ofNullable(
                dishPhotoEntityMapper.mapFromEntity(
                        dishPhotoJpaRepository.save(
                                dishPhotoEntityMapper.mapToEntity(dishPhoto)
                        )
                ));
    }

    @Override
    public void deleteDishPhoto(DishPhoto dishPhoto) {
        dishPhotoJpaRepository.delete(dishPhotoEntityMapper.mapToEntity(dishPhoto));
    }

    @Override
    public Integer changePhotoName(String newPhotoName, Integer dishPhotoId) {
        return dishPhotoJpaRepository.changePhotoName(newPhotoName, dishPhotoId);
    }

    @Override
    public Optional<DishPhoto> getDishPhotoByUrl(String url) {
        return dishPhotoJpaRepository.findByOrderUrl(url).map(
                dishPhotoEntityMapper::mapFromEntity);
    }
}
