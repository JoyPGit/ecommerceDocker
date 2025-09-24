package com.sp.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sp.ecommerce.model.*;
import com.sp.ecommerce.model.enumType.UserStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.List;

public class UserResponseDTO {
    private String businessName;
    private String type;
    private Address address;
    private String businessEmail;
    private String businessPhone;
    private String businessRegNo;
    private String taxId;
    private List<BankAccountDetails> bankAccountDetails;
    private List<WareHouseTag> wareHouseTags;
    private UserStatus status;
    private Boolean isDeleted;
    private Instant createdAt;
    private Instant updatedAt;
}
