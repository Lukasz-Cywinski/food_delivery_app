package pl.project.business.dao;

import pl.project.domain.model.DeliveryMan;

import java.util.List;
import java.util.Optional;

public interface DeliveryManDAO {

    Optional<DeliveryMan> addDeliveryMan(DeliveryMan deliveryMan);

    Optional<DeliveryMan> getDeliveryManByPersonalCode(String personalCode);

    List<DeliveryMan> getActiveDeliveryMen();

    List<DeliveryMan> getAvailableDeliveryMen();

    Integer changeDeliveryManPhoneNumber(String newPhoneNumber, String personalCode);

    Integer deactivateDeliveryMan(String personalCode);

    Integer changeDeliveryManAvailabilityStatus(boolean availabilityStatus, String personalCode);


}
