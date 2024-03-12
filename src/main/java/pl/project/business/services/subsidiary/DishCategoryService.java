package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.project.business.dao.DishCategoryDAO;
import pl.project.domain.exception.EntityReadException;
import pl.project.domain.model.DishCategory;
import pl.project.infrastructure.database.entity.DishCategoryEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class DishCategoryService {

    private final String ENTITY_READ_EXCEPTION = "Fail to find resource: %s, by identifier: %s";

    private final DishCategoryDAO dishCategoryDAO;

    public List<DishCategory> getDishCategories() {
//        TODO - tests
        return dishCategoryDAO.getAllDishCategories();
    }

    public DishCategory getDishCategoryById(Integer id){
//        TODO - tests
        return dishCategoryDAO.getDishCategoryByDishCategoryId(id)
                .orElseThrow(() -> new EntityReadException(ENTITY_READ_EXCEPTION.formatted(DishCategoryEntity.class, id)));
    }
}
