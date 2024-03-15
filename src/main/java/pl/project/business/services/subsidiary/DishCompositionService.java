package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DishCompositionService {

//    private final String COMPOSITION_CREATION_EXCEPTION = "Fail to crete dish composition for order: %s";
//
//    private final DishCompositionDAO dishCompositionDAO;
//
//    // without transactional -> methods all called from another Transactional methods
//    public void deleteDishCompositions(Order order){
//        dishCompositionDAO.findDishCompositionByOrder(order)
//                .forEach(dishCompositionDAO::deleteDishComposition);
//    }
//
//    public void createDishCompositions(Order order, List<DishComposition> compositionsWithoutAssignedOrder) {
//        compositionsWithoutAssignedOrder.forEach(composition -> dishCompositionDAO.createDishComposition(composition.withOrder(order))
//                .orElseThrow(() -> new OwnerResourceCreationException(COMPOSITION_CREATION_EXCEPTION.formatted(order.getOrderCode()))));
//    }
//
//    public List<Order> getActiveOrdersForRestaurant(Restaurant restaurant){
//       return dishCompositionDAO.getActiveOrdersForRestaurant(restaurant);
//    }
//
//    public List<Order> getRealizedOrdersForRestaurant(Restaurant restaurant){
//        return dishCompositionDAO.getRealizedOrdersForRestaurant(restaurant);
//    }


}
