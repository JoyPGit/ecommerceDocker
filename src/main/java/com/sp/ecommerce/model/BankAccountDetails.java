package com.sp.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BankAccountDetails {
    @JsonProperty("bank_name")
    private String bankName;

    @Size(min = 8, max = 15, message = "Account Number must be between 8 and 15 " +
            "characters.")
    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_holder_name")
    private String accountHolderName;
}
