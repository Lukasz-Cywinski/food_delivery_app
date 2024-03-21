package pl.project.business.dao;

import org.springframework.web.multipart.MultipartFile;
import pl.project.domain.model.DishPhoto;

import java.net.URL;

public interface DishPhotoFileStorageDAO {
    void savePhotoInStorage(MultipartFile dishPhotoContent, DishPhoto dishPhoto);
    void updatePhotoInStorage(MultipartFile dishPhotoContent, String dishPhotoURL);
    void deletePhotoInStorage(String dishPhotoURL);


}
