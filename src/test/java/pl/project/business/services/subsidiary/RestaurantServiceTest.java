package pl.project.business.services.subsidiary;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

//    @Mock
//    private RestaurantDAO restaurantDAO;
//    @Mock
//    private PageableService pageableService;
//
//    @InjectMocks
//    private RestaurantService restaurantService;
//
//    InstanceMapper instanceMapper = new InstanceMapper();
//
//    @Test
//    void getActiveRestaurantsTest() {
//        //given
//        Restaurant restaurant1 = instanceMapper.mapFromEntity(someRestaurant1());
//        Restaurant restaurant2 = instanceMapper.mapFromEntity(someRestaurant2());
//        List<Restaurant> restaurants = List.of(restaurant1, restaurant2);
//        PageableProperties pageableProperties = PageableProperties.builder()
//                .pageNumber(0)
//                .objectsPerPage(5)
//                .sortedBy("name")
//                .isAscending(true)
//                .build();
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
//        when(pageableService.buildPageable(pageableProperties)).thenReturn(pageable);
//        when(restaurantDAO.findActiveRestaurants(pageable)).thenReturn(restaurants);
//
//        //when
//        List<Restaurant> result = restaurantService.getActiveRestaurants(pageableProperties);
//
//        //then
//        assertEquals(2, result.size());
//        Assertions.assertThat(result).contains(restaurant1, restaurant2);
//    }
//
//    @Test
//    void getDishesFromRestaurantTest() {
//        //given
//        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
//        Dish dish1 = instanceMapper.mapFromEntity(someDish1()).withRestaurant(restaurant);
//        Dish dish2 = instanceMapper.mapFromEntity(someDish2()).withRestaurant(restaurant);
//        List<Dish> dishes = List.of(dish1, dish2);
//        PageableProperties pageableProperties = PageableProperties.builder()
//                .pageNumber(0)
//                .objectsPerPage(5)
//                .sortedBy("name")
//                .isAscending(true)
//                .build();
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
//        when(pageableService.buildPageable(pageableProperties)).thenReturn(pageable);
//        when(restaurantDAO.findActiveDishesForRestaurant(restaurant, pageable)).thenReturn(dishes);
//
//        //when
//        List<Dish> result = restaurantService.getDishesFromRestaurant(restaurant, pageableProperties);
//
//        //then
//        assertEquals(2, result.size());
//        Assertions.assertThat(result).contains(dish1, dish2);
//    }
//
//    @Test
//    void createRestaurantTest() {
//        //given
//        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
//        String restaurantName = restaurant.getName();
//        RestaurantOwner restaurantOwner = restaurant.getRestaurantOwner();
//
//        Restaurant anotherRestaurant = instanceMapper.mapFromEntity(someRestaurant2());
//        when(restaurantDAO.createRestaurant(any())).thenReturn(Optional.of(restaurant));
//
//        //when
//        Restaurant result = restaurantService.createRestaurant(restaurantName, restaurantOwner);
//
//        //then
//        assertEquals(restaurant, result);
//    }
//
//    @Test
//    void entityCreationExceptionTest(){
//        //given
//        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
//        String restaurantName = restaurant.getName();
//        RestaurantOwner restaurantOwner = restaurant.getRestaurantOwner();
//
//        Restaurant anotherRestaurant = instanceMapper.mapFromEntity(someRestaurant2());
//        String anotherRestaurantName = anotherRestaurant.getName();
//        RestaurantOwner anotherRestaurantOwner = anotherRestaurant.getRestaurantOwner();
//        lenient().when(restaurantDAO.createRestaurant(restaurant)).thenReturn(Optional.of(restaurant));
//
//        //when
//        Exception exception = assertThrows(OwnerResourceCreationException.class,
//                () -> restaurantService.createRestaurant(anotherRestaurantName, anotherRestaurantOwner));
//
//        //then
//        assertInstanceOf(OwnerResourceCreationException.class, exception);
//        assertEquals("Fail to crete restaurant: name2",
//                exception.getMessage());
//    }
//
//    @Test
//    void deactivateRestaurantTest() {
//        //given
//        String restaurantCode = instanceMapper.mapFromEntity(someRestaurant1()).getRestaurantCode();
//        when(restaurantDAO.deactivateRestaurant(restaurantCode)).thenReturn(1);
//
//        //when
//        boolean result = restaurantService.deactivateRestaurant(restaurantCode);
//
//        //then
//        assertTrue(result);
//    }
//
//    @Test
//    void changeRestaurantNameTest() {
//        //given
//        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
//        Restaurant updatedRestaurant = instanceMapper.mapFromEntity(someRestaurant2());
//        when(restaurantDAO.changeRestaurantName(updatedRestaurant.getName(), restaurant.getRestaurantCode())).thenReturn(1);
//
//        //when
//        boolean result = restaurantService.changeRestaurantName(updatedRestaurant.getName(), restaurant.getRestaurantCode());
//
//        //then
//        assertTrue(result);
//    }
//
//    @Test
//    void changeRestaurantOwnerTest() {
//        //given
//        RestaurantOwner restaurantOwner = instanceMapper.mapFromEntity(someRestaurantOwner2());
//        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
//        when(restaurantDAO.changeRestaurantOwner(restaurantOwner, restaurant.getRestaurantCode())).thenReturn(1);
//
//        //when
//        boolean result = restaurantService.changeRestaurantOwner(restaurantOwner, restaurant.getRestaurantCode());
//
//        //then
//        assertTrue(result);
//    }
//
//    @Test
//    void getRestaurantsByRestaurantOwnerTest(){
//        //given
//        RestaurantOwner restaurantOwner = instanceMapper.mapFromEntity(someRestaurantOwner1());
//        Restaurant restaurant1 = instanceMapper.mapFromEntity(someRestaurant1()).withRestaurantOwner(restaurantOwner);
//        Restaurant restaurant2 = instanceMapper.mapFromEntity(someRestaurant1()).withRestaurantOwner(restaurantOwner);
//        List<Restaurant> restaurants = List.of(restaurant1, restaurant2);
//        when(restaurantDAO.findByRestaurantOwner(restaurantOwner)).thenReturn(restaurants);
//
//        //when
//        List<Restaurant> result = restaurantService.getRestaurantsByRestaurantOwner(restaurantOwner);
//
//        //then
//        assertEquals(2, result.size());
//    }
}