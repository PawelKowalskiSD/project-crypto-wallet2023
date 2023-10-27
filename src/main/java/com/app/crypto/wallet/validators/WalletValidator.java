package com.app.crypto.wallet.validators;

import org.springframework.stereotype.Component;

@Component
public class WalletValidator {
    public void validateWalletId(Long walletId) {
        if (walletId < 0)
            throw new RuntimeException("invalid wallet id");
    }
}
