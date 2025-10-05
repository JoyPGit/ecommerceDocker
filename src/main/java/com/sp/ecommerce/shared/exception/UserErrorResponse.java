package com.sp.ecommerce.shared.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User Error Response")
public class UserErrorResponse {


    @Schema(description = "Error message", example = "User not found" )
    private String message;

    @Schema(description = "HTTP status code", example = "404" )
    private int status;

    @Schema(description = "Timestamp of the error", example = "2023-10-10T14:48:00.000Z" )
    private Instant timestamp;

}
