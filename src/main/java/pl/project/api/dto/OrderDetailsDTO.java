package pl.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {

    String orderCode;
    String receivedDateTime;
    String completedDateTime;
    String customerEmail;
    String customerPhoneNumber;
    String customerCity;
    String customerStreet;
    String customerPostalCode;
    String deliveryServiceCode;

}
