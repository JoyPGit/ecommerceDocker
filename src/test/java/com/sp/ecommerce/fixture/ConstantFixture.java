package com.sp.ecommerce.fixture;

import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.entity.UserEntity;
import com.sp.ecommerce.modules.users.model.*;
import com.sp.ecommerce.modules.users.model.enumType.*;
import com.sp.ecommerce.shared.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.*;

public class ConstantFixture {


    public static final String BUSINESS_NAME = "ecomuser1@gmail.com";
    public static final String BUILDING_NO = "Building1";
    public static final String STREET_CITY = "Street_BBS_1";
    public static final Integer PIN = 751002;
    public static final String STATE = "State1";
    public static final String USER_EMAIL = "ecomuser1@gmail.com";
    public static final String USER_PHONE = "9578451512";
    public static final String BUSINESS_REG_NO = "business_reg_1";
    public static final String TAX_ID = "tax_id_1";
    public static final String BANK_NAME = "bank_1";
    public static final String ACCOUNT_NUMBER = "123456789";
    public static final String ACCOUNT_HOLDER_NAME = "account_holder_1";
    public static final String WH_TAG_KEY_1 = "wh_tag_key_1";
    public static final String WH_TAG_VALUE_1 = "wh_tag_value_1";

    private static UserMapper userMapper;
    private static UUID USER_ID;

    static {
        userMapper = UserMapper.INSTANCE;
        USER_ID = UUID.randomUUID();
    }

    public static Address getAddress(){
        return Address.builder()
                .BuildingNo(BUILDING_NO)
                .streetCity(STREET_CITY)
                .pin(PIN)
                .state(STATE)
                .build();
    }

    public static List<BankAccountDetails> getBankAcctDetails(){
        return List.of(
                BankAccountDetails.builder()
                        .bankName(BANK_NAME)
                        .accountHolderName(ACCOUNT_HOLDER_NAME)
                        .accountNumber(ACCOUNT_NUMBER)
                        .build()
        );
    }

    public static List<WareHouseTag> getWareHouseTags(){
        return List.of(
                WareHouseTag.builder()
                        .whTagKey(WH_TAG_KEY_1)
                        .whTagValue(WH_TAG_VALUE_1)
                        .build()
        );
    }

    public static UserRequestDTO getUserRequestDTO(){
        return UserRequestDTO.builder()
                .businessName(BUSINESS_NAME)
                .type(UserType.DISTRIBUTOR)
                .address(getAddress())
                .businessEmail(USER_EMAIL)
                .businessPhone(USER_PHONE)
                .businessRegNo(BUSINESS_REG_NO)
                .taxId(TAX_ID)
                .bankAccountDetails(getBankAcctDetails())
                .wareHouseTags(getWareHouseTags())
                .status(UserStatus.ACTIVE)
                .build();
    }

    public static UserEntity getPreSaveUserEntity(){
        return userMapper.toUserEntity(getUserRequestDTO());
    }

    public static UserEntity getPostSaveUserEntity(){
        UserEntity userEntity = userMapper.toUserEntity(getUserRequestDTO());
        userEntity.setUserId(USER_ID);
        userEntity.setIsDeleted(Boolean.FALSE);
        userEntity.setCreatedAt(Instant.now());
        userEntity.setUpdatedAt(Instant.now());
        return userEntity;
    }

    public static UserResponseDTO getUserResponseDTO() {
        return userMapper.toResponseDto(getPostSaveUserEntity());
    }
}
