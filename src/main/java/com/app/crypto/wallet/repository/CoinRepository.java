package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.Coin;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Transactional
@Repository
public interface CoinRepository extends CrudRepository<Coin, Long> {
    @Override
    Optional<Coin> findById(Long id);

    Coin findCoinsByCoinNameAndWallet_WalletId(String name, Long walletId);
}
