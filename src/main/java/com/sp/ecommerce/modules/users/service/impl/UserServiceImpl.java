package com.sp.ecommerce.modules.users.service.impl;

import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.entity.*;
import com.sp.ecommerce.modules.users.repository.*;
import com.sp.ecommerce.modules.users.service.UserService;
import com.sp.ecommerce.shared.exception.ResourceNotFoundException;
import com.sp.ecommerce.shared.utils.DocumentUtil;
import com.sp.ecommerce.shared.utils.mapper.UserPOJOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;
import java.util.*;

import static com.sp.ecommerce.shared.utils.Constants.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    private final UserPOJOMapper userPOJOMapper;

    private final DocumentRepository documentRepository;

    @Autowired
    UserServiceImpl(UserRepository repository, UserPOJOMapper mapper,
                    DocumentRepository documentRepository){
        this.repository = repository;
        this.userPOJOMapper = mapper;
        this.documentRepository = documentRepository;
    }

    @Cacheable(value = REDIS_KEY_USER, key = "#userId")
    @Override
    public UserResponseDTO findUserByUserId(String userId) {
        Optional<UserEntity> userEntity =
                Optional.ofNullable(this.repository.findByUserId(UUID.fromString(userId))
                        .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + " for id " + userId)));
        return userPOJOMapper.toResponseDto(userEntity.get());
    }


    /**
     * mapstruct mapper -> convert reqDTO to entity
     *                  -> convert entity to respDTO
     *
     * @param requestDTO
     * @return
     */
    @Caching(put = {
            @CachePut(value = REDIS_KEY_USER, key = "#result.userId")
    })
    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        UserEntity userEntity = this.userPOJOMapper.toUserEntity(requestDTO);
        UserEntity savedEntity =  this.repository.save(userEntity);
        // override toString for UserEntity?
        log.info("user info saved in db {}", savedEntity.getUserId());
        return this.userPOJOMapper.toResponseDto(savedEntity);
    }

    @Override
    public void deleteUserById(String userId){
        this.repository.softDeleteByUserId(UUID.fromString(userId));
    }

    @Override
    public DocumentEntity uploadDocument(MultipartFile file, String userId){
        try {
            DocumentEntity documentEntity
                    = DocumentEntity.builder()
                    .documentName(file.getOriginalFilename())
                    .userId(UUID.fromString(userId))
                    .type(file.getContentType())
                    .documentData(DocumentUtil.compress(file.getBytes()))
                    .build();
            return this.documentRepository.save(documentEntity);
        } catch (IOException exception){
            log.error("Exception occurred while uploading document for userId {} , error {}",
                    userId, exception.getMessage());
            throw new RuntimeException("Failed to upload document");
        }
    }
}
