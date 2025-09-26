package com.sp.ecommerce.fixture;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.testcontainers.utility.DockerImageName;

/**
 * Shared base class for integration tests using a single static PostgreSQL container.
 */

@Testcontainers

public abstract class AbstractPostgresTest {

    @ServiceConnection
    @Container
    protected static PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
}
