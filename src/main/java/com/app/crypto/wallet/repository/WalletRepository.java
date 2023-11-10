package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {
    @Override
    List<Wallet> findAll();
    Optional<Wallet> findByWalletId(Long id);
    List<Wallet> findWalletsByUser_UserId(Long userId);
}
