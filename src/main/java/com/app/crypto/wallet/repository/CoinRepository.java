package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.Coin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoinRepository extends CrudRepository<Coin, Long> {
    @Override
    Optional<Coin> findById(Long id);
}
