package com.codeassmnt.voucherpool.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Data
@Schema(description = "Request object for validating a voucher code")
public class ValidateVoucherRequest {
    @NotBlank(message = "Voucher code is required")
    @Pattern(regexp = "^[A-Z0-9]{8,16}$", message = "Voucher code must be 8-16 characters long and contain only uppercase letters and numbers")
    @Schema(description = "The voucher code to validate", example = "SUMMER2025", required = true)
    private String code;

    @NotBlank(message = "Email address is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address of the voucher recipient", example = "user@example.com", 
            required = true)
    private String email;
}
