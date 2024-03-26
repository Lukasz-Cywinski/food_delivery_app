package pl.project.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.project.infrastructure.security.Role;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Pattern(regexp = "^[A-Za-z0-9_]{1,32}$")
    String userName;

    @Email
    String email;

    String password;
}
