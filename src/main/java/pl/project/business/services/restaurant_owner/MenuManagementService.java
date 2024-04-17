package pl.project.business.services.restaurant_owner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.project.business.dao.*;
import pl.project.domain.exception.restaurant_owner.OwnerResourceCreateException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceDeleteException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceUpdateException;
import pl.project.domain.model.Dish;
import pl.project.domain.model.DishCategory;
import pl.project.domain.model.DishPhoto;
import pl.project.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static pl.project.api.controller.addresses.ResourcePaths.URL_TO_PHOTO_STORAGE_WITH_FORMATTER;
import static pl.project.domain.exception.ExceptionMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class MenuManagementService {

    private final DishDAO dishDAO;
    private final DishPhotoDAO dishPhotoDAO;
    private final RestaurantDAO restaurantDAO;
    private final DishCategoryDAO dishCategoryDAO;
    private final DishPhotoFileStorageDAO dishPhotoFileStorageDAO;

    @Transactional
    public Dish createDish(Dish dish, MultipartFile dishPhotoContent) {
        try {
            DishPhoto dishPhoto = null;
            if (!dishPhotoContent.isEmpty()) {
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
        dishPhotoFileStorageDAO.savePhotoInStorage(dishPhotoContent, dishPhoto);
        return dishPhotoDAO.createDishPhoto(dishPhoto).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public List<Dish> getActiveDishesForRestaurant(String restaurantCode) {
        try {
            return dishDAO.findActiveDishesByRestaurant(restaurantCode);
        } catch (Exception e) {
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION.formatted(Dish.class.getSimpleName(), restaurantCode), e);
        }
    }

    @Transactional
    public boolean deactivateDish(String dishCode) {
        try {
            return dishDAO.deactivateDish(dishCode) == 1;
        } catch (Exception e) {
            throw new OwnerResourceDeleteException(RESOURCE_DELETE_EXCEPTION.formatted(Dish.class.getSimpleName(), dishCode), e);
        }
    }

    @Transactional
    public List<DishCategory> getDishCategories() {
        try {
            return dishCategoryDAO.getAllDishCategories();
        } catch (Exception e) {
            throw new OwnerResourceReadException(RESOURCE_DELETE_EXCEPTION
                    .formatted(Dish.class.getSimpleName(), "all dish categories"), e);
        }
    }

    @Transactional
    public Dish getDishByDishCode(String dishCode) {
        try {
            return dishDAO.findDishByDishCode(dishCode).orElseThrow(RuntimeException::new);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION.formatted(Dish.class.getSimpleName(), dishCode), e);
        }
    }

    @Transactional
    public void updateDish(Dish dish, MultipartFile dishPhotoContent, String dishCode) {
        String newDishName = dish.getName();
        String newDishDescription = dish.getDescription();
        String dishPhotoUrl = dish.getDishPhoto().getUrl();
        BigDecimal newDishPrice = dish.getPrice();
        Integer newDishAveragePreparationTime = dish.getAveragePreparationTimeMin();
        Integer newDishCategoryId = dish.getDishCategory().getId();

        try {
            if (Objects.nonNull(newDishName) && !newDishName.isEmpty()) dishDAO.changeDishName(newDishName, dishCode);
            if (Objects.nonNull(newDishDescription) && !newDishDescription.isEmpty()) dishDAO.changeDishDescription(newDishDescription, dishCode);
            if (Objects.nonNull(newDishPrice)) dishDAO.changeDishPrice(newDishPrice, dishCode);
            if (Objects.nonNull(newDishAveragePreparationTime)) dishDAO.changeDishPreparationTime(newDishAveragePreparationTime, dishCode);
            if (Objects.nonNull(newDishCategoryId)) dishDAO.changeDishCategory(newDishCategoryId, dishCode);
            if (!dishPhotoContent.isEmpty()) {
                Optional<DishPhoto> dishPhoto = dishPhotoDAO.getDishPhotoByUrl(dishPhotoUrl);
                if (dishPhoto.isPresent()) dishPhotoFileStorageDAO.updatePhotoInStorage(dishPhotoContent, dishPhotoUrl);
                else addPhotoToExistingDish(dishPhotoContent, dishCode);
            }
        }
        catch (Exception e){
            throw new OwnerResourceUpdateException(RESOURCE_MODIFICATION_EXCEPTION
                    .formatted(Dish.class.getSimpleName(), dishCode));
        }
    }

    private void addPhotoToExistingDish(MultipartFile dishPhotoContent, String dishCode){
        Dish dish = dishDAO.findDishByDishCode(dishCode).orElseThrow(RuntimeException::new);
        DishPhoto dishPhoto = createDishPhoto(dishPhotoContent);
        dishDAO.createDish(dish.withDishPhoto(dishPhoto));
    }
}
