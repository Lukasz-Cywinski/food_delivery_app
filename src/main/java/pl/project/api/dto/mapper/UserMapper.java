package pl.project.api.dto.mapper;

import pl.project.api.dto.UserDTO;
import pl.project.infrastructure.security.User;

public interface UserMapper {

    User mapFromDTO(UserDTO dto);
}
