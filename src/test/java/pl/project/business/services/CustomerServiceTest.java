package pl.project.business.services;

import org.junit.jupiter.api.Test;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.util.db.CustomerInstance;


class CustomerServiceTest extends MyJpaConfiguration {

    @Test
    void addCustomer() {

        CustomerEntity customerEntity = CustomerInstance.someCustomer1();
    }
}