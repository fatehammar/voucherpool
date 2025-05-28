package com.codeassmnt.voucherpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codeassmnt.voucherpool.model.Recipient;
import java.util.Optional;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    Optional<Recipient> findByEmail(String email);
}
