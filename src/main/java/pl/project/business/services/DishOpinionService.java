package pl.project.business.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.DishOpinionDAO;
import pl.project.business.services.subsidiary.pageable.PageableService;
import pl.project.domain.exception.restaurant_owner.OwnerResourceCreateException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.model.Dish;
import pl.project.domain.model.DishOpinion;
import pl.project.domain.model.PageableProperties;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class DishOpinionService {

    private final String OPINION_CREATION_EXCEPTION = "Fail to create opinion by user: %s for dish: %s";
    private final String OPINION_READ_EXCEPTION = "Fail to calculate average evaluation for dish: %s";

    private DishOpinionDAO dishOpinionDAO;
    private PageableService pageableService;

    public DishOpinion createDishOpinion(DishOpinion dishOpinion){
        return dishOpinionDAO.createDishOpinion(dishOpinion)
                .orElseThrow(() -> new OwnerResourceCreateException(OPINION_CREATION_EXCEPTION
                .formatted(dishOpinion.getCustomer(), dishOpinion.getDish())));
    }

    @Transactional
    public BigDecimal getAverageDishEvaluation(Dish dish){
        return dishOpinionDAO.getAverageDishEvaluation(dish)
                .orElseThrow(() -> new OwnerResourceReadException(OPINION_READ_EXCEPTION.formatted(dish)));
    }

    @Transactional
    public List<DishOpinion> getDishOpinions(Dish dish, PageableProperties pageableProperties){
        return dishOpinionDAO.findDishOpinionsByDish(dish, pageableService.buildPageable(pageableProperties));
    }
}
