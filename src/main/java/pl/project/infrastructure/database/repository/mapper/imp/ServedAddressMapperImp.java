package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.database.entity.ServedAddressEntity;
import pl.project.infrastructure.database.repository.mapper.ServedAddressMapper;

@Component
public class ServedAddressMapperImp implements ServedAddressMapper {

    public ServedAddress mapFromEntity(ServedAddressEntity entity){
        return null;
    }

    public ServedAddressEntity mapToEntity(ServedAddress domainObj){
        return null;
    }
}
