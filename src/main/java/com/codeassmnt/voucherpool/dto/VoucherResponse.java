package com.codeassmnt.voucherpool.dto;

import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Data
@Schema(description = "Response object containing voucher details")
public class VoucherResponse {
    @NotNull(message = "Voucher code cannot be null")
    @Pattern(regexp = "^[A-Z0-9]{8,16}$", message = "Voucher code must be 8-16 characters long and contain only uppercase letters and numbers")
    @Schema(description = "Unique voucher code", example = "SUMMER2025")
    private String code;

    @NotBlank(message = "Special offer name is required")
    @Schema(description = "Name of the special offer this voucher is for", example = "Summer Sale 2025")
    private String specialOfferName;

    @NotNull(message = "Percentage discount is required")
    @DecimalMin(value = "0.0", message = "Percentage discount must be at least 0")
    @DecimalMax(value = "100.0", message = "Percentage discount cannot exceed 100")
    @Schema(description = "Discount percentage offered by this voucher", 
        example = "25.0", minimum = "0", maximum = "100")
    private Double percentageDiscount;

    @NotNull(message = "Expiration date is required")
    @Schema(description = "Date and time when this voucher expires", example = "2025-12-31T23:59:59")
    private LocalDateTime expirationDate;

    @Schema(description = "Whether this voucher has been used", example = "false")
    private boolean used;

    @Schema(description = "Date and time when this voucher was used, null if unused", example = "2025-06-01T10:30:00")
    private LocalDateTime usedDate;
}
