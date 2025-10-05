package com.sp.ecommerce.modules.users.service;

import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.entity.DocumentEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponseDTO findUserByUserId(String userId);

    UserResponseDTO createUser(UserRequestDTO requestDTO);

    void deleteUserById(String userId);

    DocumentEntity uploadDocument(MultipartFile file, String userId);

}
