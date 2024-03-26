package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.UserDTO;
import pl.project.api.dto.mapper.UserMapper;
import pl.project.infrastructure.security.User;

@Component
public class UserMapperImp implements UserMapper {
    @Override
    public User mapFromDTO(UserDTO userDTO) {
        return User.builder()
                .userName(userDTO.getUserName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
