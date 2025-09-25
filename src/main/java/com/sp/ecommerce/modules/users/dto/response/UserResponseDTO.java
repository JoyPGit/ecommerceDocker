package com.sp.ecommerce.modules.users.dto.response;

import com.sp.ecommerce.modules.users.model.enumType.*;
import com.sp.ecommerce.modules.users.model.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID userId;
    private String businessName;
    private UserType type;
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
