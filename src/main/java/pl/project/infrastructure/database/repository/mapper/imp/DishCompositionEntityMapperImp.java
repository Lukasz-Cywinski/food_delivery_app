package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.DishComposition;
import pl.project.infrastructure.database.entity.DishCompositionEntity;
import pl.project.infrastructure.database.repository.mapper.DishCompositionEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;
import pl.project.infrastructure.database.repository.mapper.OrderEntityMapper;

@Component
@AllArgsConstructor
public class DishCompositionEntityMapperImp implements DishCompositionEntityMapper {

    private final DishEntityMapper dishEntityMapper;
    private final OrderEntityMapper orderEntityMapper;

    public DishComposition mapFromEntity(DishCompositionEntity entity){
        return DishComposition.builder()
                .quantity(entity.getQuantity())
                .dish(dishEntityMapper.mapFromEntity(entity.getDish()))
                .order(orderEntityMapper.mapFromEntity(entity.getOrder()))
                .build();
    }

    public DishCompositionEntity mapToEntity(DishComposition domainObj){
        return DishCompositionEntity.builder()
                .quantity(domainObj.getQuantity())
                .dish(dishEntityMapper.mapToEntity(domainObj.getDish()))
                .order(orderEntityMapper.mapToEntity(domainObj.getOrder()))
                .build();
    }
}
