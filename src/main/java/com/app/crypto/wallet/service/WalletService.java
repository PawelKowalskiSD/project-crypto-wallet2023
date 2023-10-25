package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final AuthConfig authConfig;

    public List<Wallet> findAllWallets() throws UserPermissionsException {

        long userId = authConfig.getUserIdFromAuthentication();
        return walletRepository.findAll().stream()
                .filter(wallet -> wallet.getUser().getUserId() == userId)
                .collect(Collectors.toList());
    }

    public Wallet findWallet(Long walletId) throws WalletNotFoundException {
        long userId = 1L;
        return walletRepository.findWalletByWalletIdAndUser_UserId(walletId, userId).orElseThrow(WalletNotFoundException::new);
    }

    public Wallet createNewWallet(Wallet wallet) {
        wallet.setWalletName(wallet.getWalletName());
        walletRepository.save(wallet);
        return wallet;
    }

    public Wallet editWallet(Wallet wallet) {
        Optional<Wallet> findWallet = walletRepository.findByWalletId(wallet.getWalletId());
        if (findWallet.isPresent()) {
            if (wallet.getWalletName() != null) {
                wallet.setWalletName(wallet.getWalletName());
            }
            walletRepository.save(wallet);
        }
        return wallet;
    }

    public void deleteWalletByWalletId(Long walletId) {
        walletRepository.deleteById(walletId);
    }
}
