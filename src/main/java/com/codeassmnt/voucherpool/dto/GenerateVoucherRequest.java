package com.codeassmnt.voucherpool.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Data
@Schema(description = "Request object for generating voucher codes")
public class GenerateVoucherRequest {
    @NotNull(message = "Special offer ID is required")
    @Positive(message = "Special offer ID must be a positive number")
    @Schema(description = "ID of the special offer to create vouchers for",  example = "1", required = true)
    private Long specialOfferId;

    @NotNull(message = "Expiration date is required")
    @Future(message = "Expiration date must be in the future")
    @Schema(description = "Expiration date and time for the vouchers", 
            example = "2025-12-31T23:59:59", required = true)
    private LocalDateTime expirationDate;

    @NotNull(message = "Recipient emails list is required")
    @Size(min = 1, message = "At least one recipient email is required")
    @Schema(description = "List of email addresses to generate vouchers for", 
            example = "[\"user1@example.com\", \"user2@example.com\"]", required = true)
    private List<@Email(message = "Invalid email format") String> recipientEmails;
}
