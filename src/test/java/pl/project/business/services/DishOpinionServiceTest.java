package pl.project.business.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DishOpinionServiceTest {

//    @InjectMocks DishOpinionService dishOpinionService;
//
//    @Mock
//    private DishOpinionDAO dishOpinionDAO;
//    @Mock
//    private PageableService pageableService;
//
//    private final InstanceMapper instanceMapper = new InstanceMapper();
//
//    @Test
//    void createDishOpinionTest() {
//        //given
//        DishOpinion dishOpinion = instanceMapper.mapFromEntity(someDishOpinion1());
//        DishOpinion anotherDishOpinion = instanceMapper.mapFromEntity(someDishOpinion2());
//        when(dishOpinionDAO.createDishOpinion(dishOpinion)).thenReturn(Optional.of(dishOpinion));
//
//        //when
//        DishOpinion result = dishOpinionService.createDishOpinion(dishOpinion);
//        Exception exception = assertThrows(OwnerResourceCreationException.class, () -> dishOpinionService.createDishOpinion(anotherDishOpinion));
//
//        //then
//        assertEquals(dishOpinion, result);
//        assertInstanceOf(OwnerResourceCreationException.class, exception);
//        assertEquals("Fail to create opinion by user: " +
//                "Customer(name=name2, surname=surname2, phoneNumber=222, email=email2@mail.com) for dish: " +
//                "Dish(name=name2, description=description2, price=2.0, averagePreparationTimeMin=32)", exception.getMessage());
//    }
//
//    @Test
//    void getAverageDishEvaluationTest() {
//        //given
//        DishOpinion dishOpinion1 = instanceMapper.mapFromEntity(someDishOpinion1());
//        DishOpinion dishOpinion2 = dishOpinion1.withProductEvaluation(BigDecimal.TWO);
//        Dish dish = dishOpinion1.getDish();
//        BigDecimal averageEvaluation = dishOpinion1.getProductEvaluation()
//                .add(dishOpinion2.getProductEvaluation()).divide(BigDecimal.TWO, 2, RoundingMode.HALF_UP);
//        when(dishOpinionDAO.getAverageDishEvaluation(dish)).thenReturn(Optional.of(averageEvaluation));
//
//        //when
//        BigDecimal result = dishOpinionService.getAverageDishEvaluation(dish);
//
//        //then
//        assertEquals(averageEvaluation, result);
//    }
//
//    @Test
//    void getDishOpinionsTest() {
//        //given
//        DishOpinion dishOpinion1 = instanceMapper.mapFromEntity(someDishOpinion1());
//        Dish dish = dishOpinion1.getDish();
//        DishOpinion dishOpinion2 = instanceMapper.mapFromEntity(someDishOpinion2()).withDish(dish);
//        PageableProperties pageableProperties = PageableProperties.builder()
//                .pageNumber(0)
//                .objectsPerPage(5)
//                .sortedBy("productEvaluation")
//                .isAscending(true)
//                .build();
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("productEvaluation").ascending());
//        when(pageableService.buildPageable(pageableProperties)).thenReturn(pageable);
//        when(dishOpinionDAO.findDishOpinionsByDish(dish, pageable)).thenReturn(List.of(dishOpinion1, dishOpinion2));
//
//        //when
//        List<DishOpinion> opinions = dishOpinionService.getDishOpinions(dish, pageableProperties);
//
//        //then
//        assertEquals(2, opinions.size());
//        assertThat(opinions).contains(dishOpinion1, dishOpinion2);
//    }
}