package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.DishComposition;
import pl.project.infrastructure.database.entity.DishCompositionEntity;
import pl.project.infrastructure.database.repository.mapper.DishCompositionEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;
import pl.project.infrastructure.database.repository.mapper.OrderEntityMapper;

import java.util.Objects;

@Component
@AllArgsConstructor
public class DishCompositionEntityMapperImp implements DishCompositionEntityMapper {

    private final DishEntityMapper dishEntityMapper;
    private final OrderEntityMapper orderEntityMapper;

    public DishComposition mapFromEntity(DishCompositionEntity entity){
        return DishComposition.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .dish(Objects.isNull(entity.getDish()) ?
                        null : dishEntityMapper.mapFromEntity(entity.getDish()))
                .order(Objects.isNull(entity.getOrder()) ?
                        null : orderEntityMapper.mapFromEntity(entity.getOrder()))
                .build();
    }

    public DishCompositionEntity mapToEntity(DishComposition domainObj){
        return DishCompositionEntity.builder()
                .id(domainObj.getId())
                .quantity(domainObj.getQuantity())
                .dish(Objects.isNull(domainObj.getDish()) ?
                        null : dishEntityMapper.mapToEntity(domainObj.getDish()))
                .order(Objects.isNull(domainObj.getOrder()) ?
                        null : orderEntityMapper.mapToEntity(domainObj.getOrder()))
                .build();
    }
}
