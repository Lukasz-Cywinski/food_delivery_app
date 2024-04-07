package pl.project.api.controller.thymeleaf.customer;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.dto.*;
import pl.project.api.dto.mapper.DishMapper;
import pl.project.api.dto.mapper.PageablePropertiesMapper;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.business.services.customer.OrderCreationService;
import pl.project.business.services.pageable.PageableService;
import pl.project.domain.model.PageableProperties;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static pl.project.api.controller.addresses.CustomerAddresses.ORDER_CREATION;

@Controller
@AllArgsConstructor
@RequestMapping(ORDER_CREATION)
public class OrderCreationController {

    static final String RESTAURANT_DISH_LIST = "/{restaurantCode}";
    static final String REDIRECT_RESTAURANT_DISH_LIST = "redirect:%s%s".formatted(ORDER_CREATION, RESTAURANT_DISH_LIST);

    private final ProjectUserDetailsService projectUserDetailsService;
    private final PageableService pageableService;
    private final OrderCreationService orderCreationService;
    private final RestaurantMapper restaurantMapper;
    private final PageablePropertiesMapper pageablePropertiesMapper;
    private final DishMapper dishMapper;

    @GetMapping
    public ModelAndView orderCreation(
            PageablePropertiesDTO pageablePropertiesDTO
    ) {
        //TODO input validation
        if(isHaveNullFields(pageablePropertiesDTO)){
            pageablePropertiesDTO = PageablePropertiesDTO.builder()
                    .pageNumber(1)
                    .sortedBy("restaurant.name")
                    .objectsPerPage(20)
                    .isAscending("true")
                    .build();
        }
        return new ModelAndView("customer/order_creation",
                populateOrderCreationWithData(pageablePropertiesDTO));
    }

    private boolean isHaveNullFields(PageablePropertiesDTO pageablePropertiesDTO) {
        return Objects.isNull(pageablePropertiesDTO.getPageNumber()) ||
                Objects.isNull(pageablePropertiesDTO.getObjectsPerPage()) ||
                Objects.isNull(pageablePropertiesDTO.getSortedBy()) ||
                Objects.isNull(pageablePropertiesDTO.getIsAscending());
    }

    private Map<String, ?> populateOrderCreationWithData(PageablePropertiesDTO pageablePropertiesDTO) {
        PageableProperties pageableProperties = pageablePropertiesMapper.mapFromDTO(pageablePropertiesDTO);
        String customerEmail = getActiveUserEmail();
        PageNavigationDTO pageNavigationDTO = pageableService.buildPageNavigation(
                orderCreationService.countAllRestaurantsForYourAddress(customerEmail), pageableProperties);

        List<RestaurantDTO> restaurantDTOs = orderCreationService.getRestaurantsForYourAddress(
                        pageableProperties, customerEmail).stream()
                .map(restaurantMapper::mapToDTO)
                .toList();

        return Map.of(
                "restaurantDTOs", restaurantDTOs,
                "pageablePropertiesDTO", pageablePropertiesDTO,
                "pageNavigationDTO", pageNavigationDTO
        );
    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping(RESTAURANT_DISH_LIST)
    public ModelAndView restaurantDishList(
            @PathVariable String restaurantCode
    ){
        return new ModelAndView("customer/restaurant_dish_list",
                populateRestaurantCodeWithData(restaurantCode));
    }

    private Map<String, ?> populateRestaurantCodeWithData(String restaurantCode) {
        List<DishDTO> dishDTOs = orderCreationService.getActiveDishesForRestaurant(restaurantCode).stream()
                .map(dishMapper::mapToDTO)
                .toList();
        List<String> dishList = new ArrayList<>();
        for (int i = 0; i < dishDTOs.size(); i++) dishList.add("");

        return Map.of(
                "dishDTOs", dishDTOs,
                "dishList", DataListDTO.builder().items(dishList).build(),
                "someTest", "my test!!"
        );
    }

    @PostMapping(RESTAURANT_DISH_LIST)
    public String createOrder(
            @PathVariable String restaurantCode,
            DataListDTO dishList
    ){
        System.out.println();
        return REDIRECT_RESTAURANT_DISH_LIST; // add new page, some kind of confirm
    }
}
