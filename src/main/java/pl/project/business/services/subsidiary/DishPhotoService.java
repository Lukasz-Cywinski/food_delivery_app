package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.project.api.controller.addresses.ResourcePaths;
import pl.project.business.dao.DishPhotoDAO;
import pl.project.domain.exception.DishPhotoStorageException;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.model.DishPhoto;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DishPhotoService {

    private final String DISH_PHOTO_CREATION_EXCEPTION = "Fail to crete dish photo: %s";
    private final String DISH_PHOTO_SAVE_EXCEPTION = "Fail to save dish photo: %s on disc";
    private final String DISH_PHOTO_DELETE_EXCEPTION = "Fail to delete dish photo: %s from disc";

    private final DishPhotoDAO dishPhotoDAO;

    // without transactional -> methods all called from another Transactional methods
    public DishPhoto createDishPhoto(MultipartFile dishPhotoContent) {
        //TODO - tests
        String dishPhotoName = "%s_%s".formatted(UUID.randomUUID().toString(), dishPhotoContent.getOriginalFilename());
        DishPhoto dishPhoto = DishPhoto.builder()
                .name(dishPhotoName)
                .url(ResourcePaths.URL_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhotoName))
                .build();
        dishPhoto = dishPhotoDAO.createDishPhoto(dishPhoto)
                .orElseThrow(() -> new EntityCreationException(DISH_PHOTO_CREATION_EXCEPTION.formatted(dishPhotoContent.getOriginalFilename())));
        if (!savePhotoInStorage(dishPhotoContent, dishPhoto)) {
            dishPhotoDAO.deleteDishPhoto(dishPhoto);
            throw new DishPhotoStorageException(DISH_PHOTO_SAVE_EXCEPTION.formatted(dishPhoto.getName()));
        }
        return dishPhoto;
    }

    public boolean deleteDishPhoto(DishPhoto dishPhoto) {
//        TODO - test
        try {
            dishPhotoDAO.deleteDishPhoto(dishPhoto);
            Files.delete(Paths.get(
                    ResourcePaths.PATH_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhoto.getName())));
        } catch (Exception e) {
            throw new DishPhotoStorageException(DISH_PHOTO_DELETE_EXCEPTION.formatted(dishPhoto.getName()));
        }
        return true;
    }

    private boolean savePhotoInStorage(MultipartFile dishPhotoContent, DishPhoto dishPhoto) {
        String str = ResourcePaths.PATH_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhoto.getName());
        try {
            dishPhotoContent.transferTo(Paths.get(
                    ResourcePaths.PATH_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhoto.getName())
            ));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
