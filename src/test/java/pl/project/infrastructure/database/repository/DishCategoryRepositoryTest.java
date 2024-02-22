package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DishCategoryEntity;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.DishCategoryJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DishCategoryInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishCategoryRepositoryTest extends MyJpaConfiguration {

    private DishCategoryJpaRepository dishCategoryJpaRepository;

    @Test
    void thatDishCategoryCanBeSavedCorrectly(){
        //given
        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishCategoryEntity dishCategory2 = someDishCategory2();
        DishCategoryEntity dishCategory3 = someDishCategory3();

        //when
        dishCategoryJpaRepository.save(dishCategory1);
        dishCategoryJpaRepository.save(dishCategory2);
        dishCategoryJpaRepository.save(dishCategory3);

        List<DishCategoryEntity> dishCategories = dishCategoryJpaRepository.findAll();

        //then
        assertThat(dishCategories)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "dishes")
                .contains(dishCategory1, dishCategory2, dishCategory3);

        assertEquals(3, dishCategories.size());
    }

    @Test
    void thatDescriptionCanBeModifiedCorrectly(){
        //given
        DishCategoryEntity dishCategory1 = someDishCategory1();
        String newDescription = "new description";

        //when
        dishCategoryJpaRepository.save(dishCategory1);
        Integer dishCategoryId = dishCategoryJpaRepository.findAll().getFirst().getId();
        dishCategoryJpaRepository.changeDescription(newDescription, dishCategoryId);

        DishCategoryEntity dishCategoryFromDb = dishCategoryJpaRepository.findById(dishCategoryId).orElseThrow();

        //then
        assertEquals(newDescription, dishCategoryFromDb.getDescription());
    }
}