package pl.project.business.dao;

import pl.project.domain.model.DishPhoto;

import java.util.Optional;

public interface DishPhotoDAO {

    Optional<DishPhoto> createDishPhoto(DishPhoto dishPhoto);

    void deleteDishPhoto (DishPhoto dishPhoto);

    Integer changePhotoName(String newPhotoName, Integer dishPhotoId);

    Optional<DishPhoto> getDishPhotoByUrl(String url);
}
