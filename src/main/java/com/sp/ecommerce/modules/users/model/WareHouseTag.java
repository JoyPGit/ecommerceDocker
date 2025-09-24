package com.sp.ecommerce.modules.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WareHouseTag {
    @JsonProperty("wh_tag_key")
    private String whTagKey;

    @JsonProperty("wh_tag_value")
    private String whTagValue;
}
