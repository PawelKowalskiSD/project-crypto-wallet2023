package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final AuthConfig authConfig;

    public List<Wallet> findAllWallets() throws UserPermissionsException {
        long validateUserId = authConfig.getUserIdFromAuthentication();
        return walletRepository.findWalletsByUser_UserId(validateUserId);
    }

    public Wallet findWallet(Long walletId) throws WalletNotFoundException, UserPermissionsException {
        Wallet wallet = walletRepository.findByWalletId(walletId).orElseThrow(WalletNotFoundException::new);
        long validateUserId = authConfig.getUserIdFromAuthentication();
        if (!(wallet.getUser().getUserId() == validateUserId))
            throw new UserPermissionsException();
        else
            return wallet;
    }

    public Wallet createNewWallet(Wallet wallet) throws UserPermissionsException, UserNotFoundException {
        long validateUserId = authConfig.getUserIdFromAuthentication();
        User user = userRepository.findByUserId(validateUserId).orElseThrow(UserNotFoundException::new);
        if (wallet.getWalletName() != null) {
            wallet.setWalletName(wallet.getWalletName());
            wallet.setUser(user);
            walletRepository.save(wallet);
        }
        return wallet;
    }

    public Wallet editWallet(Wallet wallet) throws UserPermissionsException, WalletNotFoundException {
        Wallet findWallet = walletRepository.findByWalletId(wallet.getWalletId()).orElseThrow(WalletNotFoundException::new);
        long validateUserId = authConfig.getUserIdFromAuthentication();
        if (findWallet != null && findWallet.getUser().getUserId() == validateUserId) {
            if (wallet.getWalletName() != null) {
                wallet.setWalletName(wallet.getWalletName());
                wallet.setUser(findWallet.getUser());
            }
            walletRepository.save(wallet);
            return wallet;
        } else
            throw new UserPermissionsException();
    }

    public void deleteWalletByWalletId(Long walletId) throws UserPermissionsException {
        Optional<Wallet> wallet = walletRepository.findByWalletId(walletId);
        long validateUserId = authConfig.getUserIdFromAuthentication();
        if (!(wallet.get().getUser().getUserId() == validateUserId))
            throw new UserPermissionsException();
        else
            walletRepository.deleteById(walletId);
    }
}
