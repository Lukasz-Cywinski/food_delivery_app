package pl.project.infrastructure.database.repository.configuration;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import pl.project.integration.configuration.PersistenceContainerTestConfiguration;

@DataJpaTest
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
public abstract class MyJpaConfiguration {

}
