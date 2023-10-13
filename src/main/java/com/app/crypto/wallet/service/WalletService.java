package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Wallet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class WalletService {
    public List<Wallet> findAllWallets() {
        return new ArrayList<>();
    }

    public Wallet findWallet(Long walletId) {
        return null;
    }

    public void createNewWallet(Wallet wallet) {
    }

    public Wallet editWallet(Wallet wallet) {
        return wallet;
    }

    public void deleteWalletByWalletId(Long walletId) {

    }
}
