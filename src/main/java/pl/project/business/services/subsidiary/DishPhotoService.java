package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.project.business.dao.DishPhotoDAO;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.model.DishPhoto;

@Service
@AllArgsConstructor
public class DishPhotoService {

    private final String DISH_PHOTO_CREATION_EXCEPTION = "Fail to crete dish photo: %s";

    private final DishPhotoDAO dishPhotoDAO;

    // without transactional -> methods all called from another Transactional methods
    public DishPhoto createDishPhoto(DishPhoto dishPhoto) {
        return dishPhotoDAO.createDishPhoto(dishPhoto)
                .orElseThrow(() -> new EntityCreationException(DISH_PHOTO_CREATION_EXCEPTION.formatted(dishPhoto)));
    }
}
