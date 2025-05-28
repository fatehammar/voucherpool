package com.codeassmnt.voucherpool.controller;

import org.springframework.web.bind.annotation.*;
import com.codeassmnt.voucherpool.service.VoucherService;
import com.codeassmnt.voucherpool.dto.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Voucher Management", description = "APIs for managing voucher codes and special offers")
public class VoucherController {

    private final VoucherService voucherService;

    @Operation(summary = "Generate new voucher codes", 
              description = "Generates a batch of unique voucher codes for a special offer and assigns them to recipients")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vouchers generated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", 
                     content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/generate")
    public void generateVoucherCodes(
            @Parameter(description = "Request containing special offer details and recipient list", required = true)
            @Valid @RequestBody GenerateVoucherRequest request) {
        voucherService.generateVoucherCodes(request);
    }

    @Operation(summary = "Validate and use a voucher", 
              description = "Validates a voucher code and marks it as used if valid")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Voucher validated successfully",
                     content = @Content(schema = @Schema(implementation = VoucherResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid voucher code",
                     content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "404", description = "Voucher not found",
                     content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/validate")
    public VoucherResponse validateVoucher(
            @Parameter(description = "Request containing the voucher code to validate", required = true)
            @Valid @RequestBody ValidateVoucherRequest request) {
        return voucherService.validateAndUseVoucher(request);
    }

    @Operation(summary = "Get valid vouchers for a recipient", 
              description = "Retrieves all valid (unused) vouchers assigned to a specific email address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of valid vouchers retrieved successfully",
                     content = @Content(schema = @Schema(implementation = VoucherResponse.class))),
        @ApiResponse(responseCode = "404", description = "No vouchers found for the email address",
                     content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/valid/{email}")
    public List<VoucherResponse> getValidVouchers(
            @Parameter(description = "Email address of the voucher recipient", required = true)
            @PathVariable @Email(message = "Invalid email format") String email) {
        try {
            return voucherService.getValidVouchersByEmail(email);
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error getting valid vouchers: " + e.getMessage(),
                e
            );
        }
    }
}
