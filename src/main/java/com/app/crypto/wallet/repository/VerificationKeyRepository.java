package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.VerificationKey;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Transactional
@Repository
public interface VerificationKeyRepository extends CrudRepository<VerificationKey, Long> {
    VerificationKey findByValue(String value);
}
