package com.sp.ecommerce;

import com.sp.ecommerce.fixture.AbstractPostgresTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.testcontainers.utility.DockerImageName;

/**
 * Earlier we needed h2 as spring context required a db, now TestContainer
 * provides that, no need to specify anything in application-test.properties;
 * As @ServiceConnection picks up metadata directly
 *
 * why @TestContainers -> not used? container spun up in parent class
 * (AbstractPostgresTest)
 * 
 * @ActiveProfiles("test")  // why? so it picks vals from application-test.properties
 */
@SpringBootTest
//@Testcontainers
@ActiveProfiles("test")
class EcommerceApplicationTests extends AbstractPostgresTest {


//    @Container
//    @ServiceConnection
//    static PostgreSQLContainer<?> DB_CONTAINER = new PostgreSQLContainer<>(
//            DockerImageName.parse(PostgreSQLContainer.IMAGE).withTag("16-alpine"));


    @Test
	void contextLoads() {
	}

}
