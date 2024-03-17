package pl.project.business.services.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.project.business.dao.DishCategoryDAO;
import pl.project.business.dao.DishDAO;
import pl.project.business.dao.DishPhotoDAO;
import pl.project.business.dao.RestaurantDAO;
import pl.project.domain.exception.restaurant_owner.OwnerDishPhotoStorageException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceCreateException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceDeleteException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.model.Dish;
import pl.project.domain.model.DishCategory;
import pl.project.domain.model.DishPhoto;
import pl.project.domain.model.Restaurant;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static pl.project.api.controller.addresses.ResourcePaths.PATH_TO_PHOTO_STORAGE_WITH_FORMATTER;
import static pl.project.api.controller.addresses.ResourcePaths.URL_TO_PHOTO_STORAGE_WITH_FORMATTER;
import static pl.project.domain.exception.ExceptionMessages.*;

@Service
@AllArgsConstructor
public class MenuManagementService {

    private final DishDAO dishDAO;
    private final DishPhotoDAO dishPhotoDAO;
    private final RestaurantDAO restaurantDAO;
    private final DishCategoryDAO dishCategoryDAO;

    @Transactional
    public Dish createDish(Dish dish, MultipartFile dishPhotoContent) {
        try {
            DishPhoto dishPhoto = null;
            if(!dishPhotoContent.isEmpty()){
                dishPhoto = createDishPhoto(dishPhotoContent);
            }
            DishCategory dishCategory = dishCategoryDAO.getDishCategoryByDishCategoryId(dish.getDishCategory().getId())
                    .orElseThrow(RuntimeException::new);
            Restaurant restaurant = restaurantDAO.findRestaurantByRestaurantCode(dish.getRestaurant().getRestaurantCode())
                    .orElseThrow(RuntimeException::new);
            return dishDAO.createDish(dish
                    .withDishPhoto(dishPhoto)
                    .withDishCode(UUID.randomUUID().toString())
                    .withRestaurant(restaurant)
                    .withDishCategory(dishCategory)
                    .withActive(true))
                    .orElseThrow(RuntimeException::new);
        } catch (Exception e) {
            throw new OwnerResourceCreateException(RESOURCE_CREATION_EXCEPTION
                    .formatted(Dish.class.getSimpleName(), dish.getName()), e);
        }
    }

    private DishPhoto createDishPhoto(MultipartFile dishPhotoContent) {
        String dishPhotoName = "%s_%s".formatted(UUID.randomUUID().toString(), dishPhotoContent.getOriginalFilename());
        DishPhoto dishPhoto = DishPhoto.builder()
                .name(dishPhotoName)
                .url(URL_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhotoName))
                .build();
        savePhotoInStorage(dishPhotoContent, dishPhoto);
        return dishPhotoDAO.createDishPhoto(dishPhoto).orElseThrow(RuntimeException::new);
    }

    private void savePhotoInStorage(MultipartFile dishPhotoContent, DishPhoto dishPhoto) {
        try {
            dishPhotoContent.transferTo(Paths.get(
                    PATH_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhoto.getName())));
        } catch (Exception e) {
            throw new OwnerDishPhotoStorageException(DISH_PHOTO_STORAGE_SAVE_EXCEPTION.formatted(dishPhoto.getName()), e);
        }
    }

//    public void deleteDishPhoto(String dishPhotoURL) {
//        String dishPhotoName = Arrays.asList(dishPhotoURL.split("/")).getLast();
//        try {
//            dishPhotoDAO.deleteDishPhoto(dishPhoto);
//            Files.delete(Paths.get(
//                    PATH_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted(dishPhotoName)));
//        } catch (Exception e) {
//            throw new OwnerDishPhotoStorageException(DISH_PHOTO_STORAGE_DELETE_EXCEPTION.formatted(dishPhotoName));
//        }
//    }

    @Transactional
    public List<Dish> getActiveDishesForRestaurants(String restaurantCode) {
        try {
            return dishDAO.findActiveDishesByRestaurant(restaurantCode);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION.formatted(Dish.class.getSimpleName(), restaurantCode), e);
        }
    }

    @Transactional
    public boolean deactivateDish(String dishCode) {
        try {
            return dishDAO.deactivateDish(dishCode) == 1;
        }
        catch (Exception e){
            throw new OwnerResourceDeleteException(RESOURCE_DELETE_EXCEPTION.formatted(Dish.class.getSimpleName(), dishCode), e);
        }
    }

    @Transactional
    public List<DishCategory> getDishCategories() {
        try {
            return dishCategoryDAO.getAllDishCategories();
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_DELETE_EXCEPTION
                    .formatted(Dish.class.getSimpleName(), "all dish categories"), e);
        }
    }








// TO DEAL LATER


//    public DishCategory getDishCategoryById(Integer id){
//        return dishCategoryDAO.getDishCategoryByDishCategoryId(id)
//                .orElseThrow(() -> new OwnerResourceReadException(ENTITY_READ_EXCEPTION.formatted(DishCategoryEntity.class, id)));
//    }
//
//    private final DishPhotoService dishPhotoService;
//
//    // without transactional -> methods all called from another Transactional methods
//    public Dish getDish(String dishCode) {
//        return dishDAO.findDishByDishCode(dishCode)
//                .orElseThrow(() -> new OwnerResourceReadException(DISH_READ_EXCEPTION.formatted(dishCode)));
//    }
//
//    public boolean modifyDishData(Dish dish, String dishCode) {
//        return (dishDAO.changeDishName(dish.getName(), dishCode)
//                + dishDAO.changeDishDescription(dish.getDescription(), dishCode)
//                + dishDAO.changeDishPrice(dish.getPrice(), dishCode)
//                + dishDAO.changeDishPreparationTime(dish.getAveragePreparationTimeMin(), dishCode)
//                + dishDAO.changeDishCategory(dish.getDishCategory(), dishCode))
//                > 0;
//    }

}
