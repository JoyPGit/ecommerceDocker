package com.sp.ecommerce.modules.users.service;

import com.sp.ecommerce.fixture.AbstractPostgresTest;
//import com.sp.ecommerce.integration.PostgreSQLTestContainer;
import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.entity.UserEntity;
import com.sp.ecommerce.modules.users.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static com.sp.ecommerce.fixture.ConstantFixture.getUserRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Using an abstract class, we can have all containers static and in a single
 * place, reusable and avoids individual class for specific containers
 *
 * use org.assertj.core.api.Assertions.assertThat; the one with test container
 * is for internal purpose and is unsafe, brittle, and may break with updates
 */

@SpringBootTest // for loading context
@ActiveProfiles("test")
public class UserServiceIntegrationTest extends AbstractPostgresTest {

    private final UserService userService;

    private final UserRepository userRepository;

    private UserRequestDTO userRequestDTO;

    @Autowired
    public UserServiceIntegrationTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @BeforeEach // why not BeforeAll?
    void setUp() {
        this.userRequestDTO = getUserRequestDTO();
    }

    @Test
    @DisplayName("Should persist user in DB")
    void createUserShouldPersistInDB() {
        // TODO : Format logs when configuring
        System.out.println("Container JDBC URL: " + POSTGRESQL_CONTAINER.getJdbcUrl());
        System.out.println("Container DB Name: " + POSTGRESQL_CONTAINER.getDatabaseName());
        UserResponseDTO responseDTO = this.userService.createUser(userRequestDTO);
        // check if saved in DB
        UserEntity entityInDB =
                this.userRepository.findByUserId(responseDTO.getUserId()).orElse(null);
        assertThat(entityInDB).isNotNull();
    }
}
