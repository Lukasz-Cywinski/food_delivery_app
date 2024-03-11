package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.project.business.dao.DishCategoryDAO;
import pl.project.domain.model.DishCategory;

import java.util.List;

@Service
@AllArgsConstructor
public class DishCategoryService {

    private final DishCategoryDAO dishCategoryDAO;

    public List<DishCategory> getDishCategories() {
//        TODO - tests
        return dishCategoryDAO.getAllDishCategories();
    }
}
