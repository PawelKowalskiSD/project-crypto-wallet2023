package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.Jwt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRepository extends CrudRepository<Jwt, Long> {

    Jwt findByToken(String token);
}
