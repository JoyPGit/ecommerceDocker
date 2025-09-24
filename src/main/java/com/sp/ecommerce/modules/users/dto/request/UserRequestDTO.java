package com.sp.ecommerce.modules.users.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sp.ecommerce.model.*;
import com.sp.ecommerce.modules.users.model.enumType.UserStatus;
import com.sp.ecommerce.modules.users.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserRequestDTO {
    @NotNull
    @Size(max = 256, message = "Business name can have at max 256 characters.")
    @JsonProperty("business_name")
    private String businessName;

    @JsonProperty("type")
    private String type;

    @Valid
    @JsonProperty("address")
    private Address address;

    @NotNull
    @Email
    @Size(max = 256, message = "Email can have at max 256 characters.")
    @JsonProperty("business_email")
    private String businessEmail;

    @NotNull
    @Size(min = 10, max = 16, message = "Business phone number must be between 10 and 16 characters")
    @JsonProperty("business_phone")
    private String businessPhone;

    @NotNull
    @Size(min = 8, max = 25, message = "Business registration number must be between 8" +
            " and 25 characters.")
    @JsonProperty("business_reg_no")
    private String businessRegNo;

    @NotNull
    @Size(min = 8, max = 25, message = "tax ID  must be between 8 and 25 characters.")
    @JsonProperty("tax_id")
    @Pattern(
            regexp = "^[A-Za-z0-9_@./#+\\-]*$",
            message = "Allowed special characters in tax ID are : underscore (_), at " +
                    "(@), period (.), slash (/), hash (#), pls (+), hyphen(-).")
    private String taxId;

    @NotNull
    @Size(max = 256, message = "Business name can have at max 256 characters")
    @JsonProperty("business_account_details")
    private List<BankAccountDetails> bankAccountDetails;

    @NotNull
    @JsonProperty("warehouse_tags")
    private List<WareHouseTag> wareHouseTags;

    @NotNull
    @JsonProperty("business_name")
    private UserStatus status;
}
