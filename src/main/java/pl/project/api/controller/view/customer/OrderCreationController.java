package pl.project.api.controller.view.customer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.controller.exception.CustomerIncorrectInputException;
import pl.project.api.controller.exception.ExceptionMessages;
import pl.project.api.dto.*;
import pl.project.api.dto.mapper.DishMapper;
import pl.project.api.dto.mapper.PageablePropertiesMapper;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.business.services.customer.OrderCreationService;
import pl.project.business.services.pageable.PageableService;
import pl.project.domain.model.PageableProperties;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static pl.project.api.controller.addresses.CustomerAddresses.ACTIVE_ORDERS_MANAGEMENT;
import static pl.project.api.controller.addresses.CustomerAddresses.ORDER_CREATION;
import static pl.project.api.controller.exception.ExceptionMessages.*;

@Controller
@AllArgsConstructor
@RequestMapping(ORDER_CREATION)
public class OrderCreationController {

    static final String RESTAURANT_DISH_LIST = "/{restaurantCode}";
    static final String ORDER_CONFIRMATION = "/{restaurantCode}/order_confirmation";
    static final String REDIRECT_ACTIVE_ORDERS_MANAGEMENT = "redirect:%s".formatted(ACTIVE_ORDERS_MANAGEMENT);

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
                populateDishListWithData(restaurantCode));
    }

    private Map<String, ?> populateDishListWithData(String restaurantCode) {
        List<DishDTO> dishDTOs = orderCreationService.getActiveDishesForRestaurant(restaurantCode).stream()
                .map(dishMapper::mapToDTO)
                .toList();
        List<String> dishList = new ArrayList<>();
        for (int i = 0; i < dishDTOs.size(); i++) dishList.add("");

        return Map.of(
                "dishDTOs", dishDTOs,
                "dishList", DataListDTO.builder().items(dishList).build(),
                "outputTest", ""
        );
    }

    @GetMapping(ORDER_CONFIRMATION)
    public ModelAndView orderSummary(
            @PathVariable String restaurantCode,
            DataListDTO dishList
    ){
        return new ModelAndView("customer/order_confirmation", populateOrderConfirmationWithData(dishList));
    }

    private Map<String, ?> populateOrderConfirmationWithData(DataListDTO dishList) {
        String orderCode = UUID.randomUUID().toString();
        Map<DishDTO, String> dishCompositions = dishList.getItems().stream()
                .map(composition -> composition.split(";"))
                .filter(composition -> Integer.parseInt(composition[0]) > 0)
                .collect(Collectors.toMap(
                        composition -> dishMapper.mapToDTO(orderCreationService.getDishByDishCode(composition[1])),
                        composition -> composition[0]
                ));

        BigDecimal totalPrice = dishCompositions.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer estimatedPreparationTime = dishCompositions.keySet().stream()
                .max(Comparator.comparing(DishDTO::getAveragePreparationTimeMin))
                .orElseThrow(() -> new CustomerIncorrectInputException(INCORRECT_INPUT_EXCEPTION.formatted("Preparation time","Order confirmation")))
                .getAveragePreparationTimeMin();

        DishCompositionDTO dishCompositionDTO = DishCompositionDTO.builder()
                .compositions(dishCompositions)
                .totalPrice(totalPrice)
                .orderCode(orderCode)
                .estimatedPreparationTime(estimatedPreparationTime)
                .build();
        String summaryContent;
        try {
            Map<String, String> content = dishCompositionDTO.getCompositions().entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey().getDishCode(),
                            Map.Entry::getValue
                    ));
            Type type = new TypeToken<Map<String, Integer>>() {}.getType();
            summaryContent = new Gson().toJson(content, type);
        }
        catch (Exception e){
            throw new CustomerIncorrectInputException(INCORRECT_INPUT_EXCEPTION.formatted("confirmation params", "Order confirmation"));
        }

        return Map.of(
              "dishCompositionDTO", dishCompositionDTO,
                "summaryContent", summaryContent,
                "orderCode", orderCode
        );
    }

    @PostMapping(ORDER_CONFIRMATION)
    public String orderConfirmation(
            String summaryContent,
            String orderCode
    ){
        Type type = new TypeToken<Map<String, Integer>>() {}.getType();
        Map<String, Integer> compositions;
        try {
            compositions = new Gson().fromJson(summaryContent, type);
        }
        catch (Exception e){
            throw new CustomerIncorrectInputException(INCORRECT_INPUT_EXCEPTION.formatted("confirmation params", "Order confirmation"));
        }
        orderCreationService.registerOrder(compositions, orderCode, getActiveUserEmail());
        return REDIRECT_ACTIVE_ORDERS_MANAGEMENT;
    }
}
