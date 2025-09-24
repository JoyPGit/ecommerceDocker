package com.sp.ecommerce.modules.users.service.impl;

import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.entity.UserEntity;
import com.sp.ecommerce.modules.users.service.UserService;
import com.sp.ecommerce.modules.users.repository.UserRepository;
import com.sp.ecommerce.shared.utils.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserResponseDTO findUserByUserId(String userId) {
        Optional<UserEntity> userEntity =
                this.repository.findByUserId(UUID.fromString(userId));
        return userMapper.toResponseDto(userEntity.get());
    }

    /**
     * mapstruct mapper -> convert reqDTO to entity
     *                  -> convert entity to respDTO
     *
     * @param requestDTO
     * @return
     */
    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        UserEntity userEntity = this.userMapper.toUserEntity(requestDTO);
        UserEntity savedEntity =  this.repository.save(userEntity);
        // override toString for UserEntity?
        log.info("user info saved in db {}", savedEntity.getUserId());
        return this.userMapper.toResponseDto(savedEntity);
    }
}
