package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DishPhotoEntity;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.DishPhotoJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DishPhotoInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishPhotoRepositoryTest extends MyJpaConfiguration {

    private DishPhotoJpaRepository dishPhotoJpaRepository;

    @Test
    void thatDishPhotoCanBeSavedCorrectly(){
        //given
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishPhotoEntity dishPhoto2 = someDishPhoto2();
        DishPhotoEntity dishPhoto3 = someDishPhoto3();

        //when
        dishPhotoJpaRepository.save(dishPhoto1);
        dishPhotoJpaRepository.save(dishPhoto2);
        dishPhotoJpaRepository.save(dishPhoto3);

        List<DishPhotoEntity> dishCategories = dishPhotoJpaRepository.findAll();

        //then
        assertThat(dishCategories)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "dishes")
                .contains(dishPhoto1, dishPhoto2, dishPhoto3);

        assertEquals(3, dishCategories.size());
    }

    @Test
    void thatDishPhotoCanBeModifiedCorrectly(){
        //given
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        String newName = "new name";

        //when
        dishPhotoJpaRepository.save(dishPhoto1);
        Integer dishPhotoId = dishPhotoJpaRepository.findAll().getFirst().getId();
        dishPhotoJpaRepository.changePhotoName(newName, dishPhotoId);

        DishPhotoEntity dishPhotoFromDb = dishPhotoJpaRepository.findById(dishPhotoId).orElseThrow();

        //then
        assertEquals(newName, dishPhotoFromDb.getName());
    }

    @Test
    void thatDishPhotoCanBeDeletedCorrectly(){
        //given
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishPhotoEntity dishPhoto2 = someDishPhoto2();

        //when
        dishPhotoJpaRepository.save(dishPhoto1);
        dishPhotoJpaRepository.save(dishPhoto2);

        dishPhotoJpaRepository.delete(dishPhoto1);

        List<DishPhotoEntity> photos = dishPhotoJpaRepository.findAll();

        //then
        assertEquals(1, photos.size());
    }
}