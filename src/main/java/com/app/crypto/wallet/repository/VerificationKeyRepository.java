package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.VerificationKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationKeyRepository extends CrudRepository<VerificationKey, Long> {
}
