package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserService userService;
    private final AuthConfig authConfig;

    public List<Wallet> findAllWallets() throws UserPermissionsException {
        long userId = authConfig.getUserIdFromAuthentication();
        return walletRepository.findWalletsByUser_UserId(userId);
    }

    public Wallet findWallet(Long walletId) throws WalletNotFoundException {
        return walletRepository.findByWalletId(walletId).orElseThrow(WalletNotFoundException::new);
    }

    public Wallet createNewWallet(Wallet wallet) throws UserPermissionsException, UserNotFoundException {
        long userId = authConfig.getUserIdFromAuthentication();
        User user = userService.getUserById(userId);
        if(wallet.getWalletName() != null) {
            wallet.setWalletName(wallet.getWalletName());
            wallet.setUser(user);
            walletRepository.save(wallet);
        }
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
