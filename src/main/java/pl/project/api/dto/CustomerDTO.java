package pl.project.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @Pattern(regexp = "^[A-Za-z0-9_]{1,32}$")
    String name;

    @Pattern(regexp = "^[A-Za-z0-9_]{1,32}$")
    String surname;

    @Pattern(regexp = "\\+\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}")
    String phoneNumber;

    @Email
    String email;
}
