package com.sp.ecommerce.modules.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Must be alphanumeric")
    @JsonProperty("building_no")
    private String BuildingNo;

    @NotNull
    @JsonProperty("street_city")
    private String streetCity;

    @NotNull
    private Integer pin;

    @NotNull
    private String state;
}
