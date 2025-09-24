package com.sp.ecommerce.service;

import com.sp.ecommerce.dto.request.UserRequestDTO;
import com.sp.ecommerce.dto.response.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponseDTO findUserByUserId(String userId);

    UserResponseDTO createUser(UserRequestDTO requestDTO);

}
