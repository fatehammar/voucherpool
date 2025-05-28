package com.codeassmnt.voucherpool.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.codeassmnt.voucherpool.model.*;
import com.codeassmnt.voucherpool.repository.*;
import com.codeassmnt.voucherpool.dto.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoucherService {
    
    private final VoucherCodeRepository voucherCodeRepository;
    private final RecipientRepository recipientRepository;
    private final SpecialOfferRepository specialOfferRepository;

    @Transactional
    public void generateVoucherCodes(GenerateVoucherRequest request) {
        SpecialOffer specialOffer = specialOfferRepository.findById(request.getSpecialOfferId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Special offer not found"));

        for (String email : request.getRecipientEmails()) {
            Recipient recipient = recipientRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient not found: " + email));

            VoucherCode voucherCode = new VoucherCode();
            voucherCode.setCode(generateUniqueCode());
            voucherCode.setRecipient(recipient);
            voucherCode.setSpecialOffer(specialOffer);
            voucherCode.setExpirationDate(request.getExpirationDate());
            voucherCode.setUsed(false);

            voucherCodeRepository.save(voucherCode);
        }
    }

    @Transactional
    public VoucherResponse validateAndUseVoucher(ValidateVoucherRequest request) {
        VoucherCode voucherCode = voucherCodeRepository.findByCodeAndRecipientEmail(request.getCode(), request.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voucher not found"));

        if (voucherCode.isUsed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voucher has already been used");
        }

        if (voucherCode.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voucher has expired");
        }

        voucherCode.setUsed(true);
        voucherCode.setUsedDate(LocalDateTime.now());
        voucherCodeRepository.save(voucherCode);

        VoucherResponse response = new VoucherResponse();
        response.setCode(voucherCode.getCode());
        response.setSpecialOfferName(voucherCode.getSpecialOffer().getName());
        response.setPercentageDiscount(voucherCode.getSpecialOffer().getPercentageDiscount());
        response.setExpirationDate(voucherCode.getExpirationDate());
        response.setUsed(voucherCode.isUsed());
        response.setUsedDate(voucherCode.getUsedDate());
        
        return response;
    }

    public List<VoucherResponse> getValidVouchersByEmail(String email) {
        Recipient recipient = recipientRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient not found"));

        return voucherCodeRepository.findByRecipientAndUsedFalseAndExpirationDateAfter(recipient, LocalDateTime.now())
            .stream()
            .map(voucher -> {
                VoucherResponse response = new VoucherResponse();
                response.setCode(voucher.getCode());
                response.setSpecialOfferName(voucher.getSpecialOffer().getName());
                response.setPercentageDiscount(voucher.getSpecialOffer().getPercentageDiscount());
                response.setExpirationDate(voucher.getExpirationDate());
                response.setUsed(voucher.isUsed());
                response.setUsedDate(voucher.getUsedDate());
                return response;
            })
            .collect(Collectors.toList());
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        } while (voucherCodeRepository.existsByCode(code));
        
        return code;
    }
}
