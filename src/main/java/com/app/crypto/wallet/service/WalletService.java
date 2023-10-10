package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletService {
    public List<Wallet> findAllWallets() {
        return new ArrayList<>();
    }

    public Wallet findWallet(Long walletId) {
        return null;
    }

    public void createNewWallet(Wallet wallet) {

    }

    public Wallet editWallet(Wallet wallet, Long walletId) {
        return wallet;
    }

    public void deleteWalletByWalletId(Long walletId) {

    }
}
