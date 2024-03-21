package pl.project.business.services.storage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.project.business.dao.DishPhotoFileStorageDAO;
import pl.project.domain.exception.storage.DishPhotoStorageException;
import pl.project.domain.model.DishPhoto;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static pl.project.api.controller.addresses.ResourcePaths.PATH_TO_PHOTO_STORAGE_WITH_FORMATTER;
import static pl.project.domain.exception.ExceptionMessages.*;

@Service
@AllArgsConstructor
public class DishPhotoFileStorageService implements DishPhotoFileStorageDAO {
    @Override
    public void savePhotoInStorage(MultipartFile dishPhotoContent, DishPhoto dishPhoto) {
        try {
            dishPhotoContent.transferTo(Paths.get(
                    PATH_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhoto.getName())));
        } catch (Exception e) {
            throw new DishPhotoStorageException(DISH_PHOTO_STORAGE_SAVE_EXCEPTION.formatted(dishPhoto.getName()), e);
        }
    }

    @Override
    public void updatePhotoInStorage(MultipartFile dishPhotoContent, String dishPhotoUrl) {
        String dishPhotoName = Arrays.asList(dishPhotoUrl.split("/")).getLast();
        try {
            deletePhotoInStorage(dishPhotoUrl);
            dishPhotoContent.transferTo(Paths.get(
                    PATH_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhotoName)));
        }
        catch (Exception e){
            throw new DishPhotoStorageException(DISH_PHOTO_STORAGE_UPDATE_EXCEPTION.formatted(dishPhotoName), e);
        }
    }

    @Override
    public void deletePhotoInStorage(String dishPhotoUrl) {
        String dishPhotoName = Arrays.asList(dishPhotoUrl.split("/")).getLast();
        try {
            Files.deleteIfExists(Paths.get(PATH_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhotoName)));
        }
        catch (Exception e){
            throw new DishPhotoStorageException(DISH_PHOTO_STORAGE_DELETE_EXCEPTION.formatted(dishPhotoName), e);
        }
    }
}
