package com.sp.ecommerce.service.impl;

import com.sp.ecommerce.dto.request.UserRequestDTO;
import com.sp.ecommerce.dto.response.UserResponseDTO;
import com.sp.ecommerce.repository.UserRepository;
import com.sp.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public UserResponseDTO findUserByUserId(String userId) {
        this.repository.findByUserId(UUID.fromString(userId));
//        UserResponseDTO responseDTO =
        return null;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        return null;
    }
}
