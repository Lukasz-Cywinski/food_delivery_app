package pl.project.api.dto.coverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.project.api.dto.DishCategoryDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class DishCategoryConverter implements Converter<String, DishCategoryDTO> {

    // I tried to pass whole object in select tag, but I decided to simplified things
    // I leave it as a sample how to use it, maybe I will use it someday

    //framework send object as String, and later I parse it there
    @Override
    public DishCategoryDTO convert(String source) {

        List<String> params = Arrays.stream(source.substring(source.indexOf('(') + 1, source.lastIndexOf(')'))
                        .split(",")).toList().stream()
                .map(str -> str.split("=")[1])
                .toList();

        return DishCategoryDTO.builder()
                .id(Integer.parseInt(params.getFirst()))
                .name(params.get(1))
                .description(params.get(2))
                .build();
    }

    // HTML PART WHICH ALLOW TO SEND OBJECT
//    <div class="form-group">
//        <form action="#" th:action="@{/restaurant_owner/menu_management/add_dish}" th:object="${dishDTO}" enctype="multipart/form-data" method="POST">
//            <label for="dishCategory">Choose dish category</label>
//            <select th:field="*{dishCategory}" class="form-control" id="dishCategory"> // dish category is a field DishCategoryDTO in DishDTO object
//                <option class="selected"
//                    th:each="category : ${dishCategoryDTOs}"
//                    th:value="${category}"
//                    th:text="${'Category: ' + category.name}"></option>
//            </select>
//        </form>
//    </div>
}
