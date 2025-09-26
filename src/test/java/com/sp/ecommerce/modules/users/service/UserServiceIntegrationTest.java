package com.sp.ecommerce.modules.users.service;

import com.sp.ecommerce.integration.PostgreSQLTestContainer;
import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.entity.UserEntity;
import com.sp.ecommerce.modules.users.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import static com.sp.ecommerce.fixture.ConstantFixture.getUserRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // for loading context
@ActiveProfiles("test")
public class UserServiceIntegrationTest implements PostgreSQLTestContainer {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserRequestDTO userRequestDTO;

    @BeforeEach // why not BeforeAll?
    void setUp() {
        this.userRequestDTO = getUserRequestDTO();
    }

    @Test
    @DisplayName("Should persist user in DB")
    void createUserShouldPersistInDB() {
        System.out.println("Container JDBC URL: " + DB_CONTAINER.getJdbcUrl());
        System.out.println("Container DB Name: " + DB_CONTAINER.getDatabaseName());
        UserResponseDTO responseDTO = this.userService.createUser(userRequestDTO);
        // check if saved in DB
        UserEntity entityInDB =
                this.userRepository.findByUserId(responseDTO.getUserId()).orElse(null);
        // use org.assertj.core.api.Assertions.assertThat;, the one with testcontainer
        // is for internal purpose and is unsafe, brittle, and may break with updates
        assertThat(entityInDB).isNotNull();
    }
}
