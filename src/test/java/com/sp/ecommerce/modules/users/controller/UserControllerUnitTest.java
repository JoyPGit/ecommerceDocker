package com.sp.ecommerce.modules.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.sp.ecommerce.fixture.ConstantFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerUnitTest {

    private static final String BASE_URL = "/users";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        this.requestDTO = getUserRequestDTO();
        this.responseDTO = getUserResponseDTO();
    }

    @Test
    @DisplayName("Should successfully fetch a user with a valid userId")
    void createUser_shouldReturn200_WithValidUserId() throws Exception {
        when(userService.findUserByUserId(any())).thenReturn(responseDTO);
        String userId = responseDTO.getUserId().toString();
        String GET_URL = BASE_URL + "/id/" + userId;

        mockMvc.perform(get(GET_URL))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.userId").value(responseDTO.getUserId().toString())) // UUID -> toString
                .andExpect(jsonPath("$.businessEmail").value(responseDTO.getBusinessEmail()))
                .andExpect(jsonPath("$.isDeleted").value(responseDTO.getIsDeleted()))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Should successfully create a user when a valid request is sent")
    void createUser_shouldReturn200_WithValidRequest() throws Exception {
        when(userService.createUser(any(UserRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.userId").value(responseDTO.getUserId().toString())) // UUID -> toString
                .andExpect(jsonPath("$.businessEmail").value(responseDTO.getBusinessEmail()))
                .andExpect(jsonPath("$.isDeleted").value(responseDTO.getIsDeleted()))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void createUser_shouldReturn400_whenMissingRequiredFields() throws Exception {
        UserRequestDTO invalidRequest = getUserRequestDTO();
        // leave businessName as null to trigger @NotNull
        invalidRequest.setBusinessName("");

        String json = objectMapper.writeValueAsString(invalidRequest);

        // Act + Assert
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
