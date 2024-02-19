package com.fitTracker.fit;

import com.fitTracker.fit.container.DatabaseContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {FitApplicationTests.Initializer.class})
@TestPropertySource(properties = {"spring.config.location=classpath:application.properties"})
class FitApplicationTests {
	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = DatabaseContainer.getInstance();

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"CONTAINER.USERNAME=" + postgreSQLContainer.getUsername(),
					"CONTAINER.PASSWORD=" + postgreSQLContainer.getPassword(),
					"CONTAINER.URL=" + postgreSQLContainer.getJdbcUrl()
			).applyTo(configurableApplicationContext.getEnvironment());

			postgreSQLContainer.withInitScript("classpath:resources/init.sql");

//			PostgreSQLContainer container = postgreSQLContainer
//					.withDatabaseName(postgreSQLContainer.getDatabaseName())
//					.withUsername(postgreSQLContainer.getUsername())
//					.withPassword(postgreSQLContainer.getPassword())
//					.withInitScript("classpath:resources/init.sql");
//			postgreSQLContainer.start();
		}
	}

}
