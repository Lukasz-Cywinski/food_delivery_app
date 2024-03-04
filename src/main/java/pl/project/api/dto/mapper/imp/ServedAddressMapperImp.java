package pl.project.api.dto.mapper.imp;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.api.dto.ServedAddressDTO;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.api.dto.mapper.ServedAddressMapper;
import pl.project.domain.model.ServedAddress;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ServedAddressMapperImp implements ServedAddressMapper {

    private final RestaurantMapper restaurantMapper;

    @Override
    public ServedAddressDTO mapToDTO(ServedAddress domainObj) {
        return ServedAddressDTO.builder()
                .id(domainObj.getId())
                .city(domainObj.getCity())
                .street(domainObj.getStreet())
                .restaurant(restaurantMapper.mapToDTO(domainObj.getRestaurant()))
                .build();
    }

    @Override
    public ServedAddress mapFromDTO(ServedAddressDTO dto) {
        return ServedAddress.builder()
                .id(dto.getId())
                .city(dto.getCity())
                .street(dto.getStreet())
                .restaurant(restaurantMapper.mapFromDTO(dto.getRestaurant()))
                .build();
    }
}
