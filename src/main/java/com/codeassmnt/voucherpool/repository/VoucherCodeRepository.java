package com.codeassmnt.voucherpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codeassmnt.voucherpool.model.VoucherCode;
import com.codeassmnt.voucherpool.model.Recipient;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface VoucherCodeRepository extends JpaRepository<VoucherCode, Long> {
    Optional<VoucherCode> findByCodeAndRecipientEmail(String code, String email);
    List<VoucherCode> findByRecipientAndUsedFalseAndExpirationDateAfter(Recipient recipient, LocalDateTime now);
    boolean existsByCode(String code);
}
