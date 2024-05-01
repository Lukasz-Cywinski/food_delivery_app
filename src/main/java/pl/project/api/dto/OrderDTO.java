package pl.project.api.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "orderCode")
public class OrderDTO {

    String orderCode;
    String receivedDateTime;
    String completedDateTime;
}
