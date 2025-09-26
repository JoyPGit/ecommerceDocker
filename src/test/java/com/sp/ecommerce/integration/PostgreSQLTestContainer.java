package com.sp.ecommerce.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.testcontainers.utility.DockerImageName;

/**
 *
 * The container sues postgres 16-alpine image,
 * the DB_CONTAINER is not static, as it is assigned first thing
 * when the integration test runs
 *
 * BUT mark it static
 * Why static?
 * It ensures the container is shared across test methods and started once per test class, saving time.
 * It integrates well with Spring Boot's @SpringBootTest.
 */
@Testcontainers
@SpringBootTest
public interface PostgreSQLTestContainer {

    @Container
    @ServiceConnection
    PostgreSQLContainer<?> DB_CONTAINER = new PostgreSQLContainer<>(
            DockerImageName.parse(PostgreSQLContainer.IMAGE).withTag("16-alpine"));


}
