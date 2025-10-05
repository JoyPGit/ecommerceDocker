package com.sp.ecommerce.modules.users.model;

import lombok.*;
import org.springframework.context.annotation.Bean;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDetailsResponse {
    private String documentName;
    private String type;
    private byte[] documentData;
}
