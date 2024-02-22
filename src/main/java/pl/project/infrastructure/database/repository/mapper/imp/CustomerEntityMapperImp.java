package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.domain.model.Customer;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerEntityMapperImp implements CustomerEntityMapper {

    private final DeliveryAddressEntityMapper deliveryAddressEntityMapper;

    public Customer mapFromEntity(CustomerEntity entity){
        return Customer.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .deliveryAddress(deliveryAddressEntityMapper.mapFromEntity(entity.getDeliveryAddress()))
                .isActive(entity.isActive())
                .build();
    }

    public CustomerEntity mapToEntity(Customer domainObj){
        return CustomerEntity.builder()
                .name(domainObj.getName())
                .surname(domainObj.getSurname())
                .phoneNumber(domainObj.getPhoneNumber())
                .email(domainObj.getEmail())
                .deliveryAddress(deliveryAddressEntityMapper.mapToEntity(domainObj.getDeliveryAddress()))
                .isActive(domainObj.isActive())
                .build();
    }

}
