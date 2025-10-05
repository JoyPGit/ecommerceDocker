package com.sp.ecommerce.modules.users.service;

import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import org.springframework.stereotype.Service;

public interface UserService {
    UserResponseDTO findUserByUserId(String userId);

    UserResponseDTO createUser(UserRequestDTO requestDTO);

    void deleteUserById(String userId);
}
