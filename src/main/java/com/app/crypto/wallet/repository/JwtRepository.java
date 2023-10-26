package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.Jwt;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface JwtRepository extends CrudRepository<Jwt, Long> {

    Optional<Jwt> findByToken(String token);

    @Query(value = """
            select token from Jwt token inner join User user
            on token.user.userId = user.userId
            where user.userId = :userId and (token.expired = false)
            """)
    List<Jwt> findAllJwtTokenByUser(Long userId);
}
