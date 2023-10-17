package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.JwtToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtToken, Long> {
}
