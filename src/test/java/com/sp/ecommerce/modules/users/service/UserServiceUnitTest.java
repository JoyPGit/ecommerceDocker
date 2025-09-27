package com.sp.ecommerce.modules.users.service;

import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.entity.UserEntity;
import com.sp.ecommerce.modules.users.repository.UserRepository;
import com.sp.ecommerce.modules.users.service.impl.UserServiceImpl;
import com.sp.ecommerce.shared.utils.UserPOJOMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.sp.ecommerce.fixture.ConstantFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringBootTest // why?
//@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @Mock
    private UserRepository repository;

    @Mock
    private UserPOJOMapper userPOJOMapper;

    @InjectMocks
    private UserServiceImpl userService; // impl?

    private UserEntity testUserEntity;

    @BeforeEach
    void setUp() {
        testUserEntity = getPostSaveUserEntity();
    }

    @Test
//    @Tag(CRITICAL)
    @DisplayName("findUserByUserId should return entity if found")
    void testFindUserByUserId(){
        UUID userId = testUserEntity.getUserId();
        when(repository.findByUserId(any())).thenReturn(Optional.of(testUserEntity));
        when(userPOJOMapper.toResponseDto(testUserEntity)).thenReturn(getUserResponseDTO());
        var result = this.userService.findUserByUserId(userId.toString());
        assertThat(result).isNotNull();
        assertEquals(result.getUserId(), userId);
    }

    @Test
    @DisplayName("createUser should save the entity")
    void testCreateUser(){
        UserRequestDTO requestDTO = getUserRequestDTO();
        UserEntity preSaveEntity = getPreSaveUserEntity();
        when(userPOJOMapper.toUserEntity(any(UserRequestDTO.class))).thenReturn(preSaveEntity);
        when(this.repository.save(preSaveEntity)).thenReturn(getPostSaveUserEntity());
        when(userPOJOMapper.toResponseDto(any(UserEntity.class))).thenReturn(getUserResponseDTO());
        var result = this.userService.createUser(requestDTO);
        assertThat(result).isNotNull();
    }
}
