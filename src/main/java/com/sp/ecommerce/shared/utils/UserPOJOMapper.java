package com.sp.ecommerce.shared.utils;

import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.entity.UserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // this makes it a Spring Bean
public interface UserPOJOMapper {

    UserPOJOMapper INSTANCE = Mappers.getMapper(UserPOJOMapper.class);

    UserResponseDTO toResponseDto(UserEntity user);

//    @Mapping(source = "businessName", target = "business_name")
    @Mapping(target = "isDeleted", constant = "false")
    UserEntity toUserEntity(UserRequestDTO userRequestDTO);
}

