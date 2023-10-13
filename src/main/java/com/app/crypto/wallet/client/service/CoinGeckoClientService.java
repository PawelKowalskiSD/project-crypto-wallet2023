package com.app.crypto.wallet.client.service;

import com.app.crypto.wallet.domain.Coin;
import org.springframework.stereotype.Service;

@Service
public class CoinGeckoClientService {
    public Coin searchCoin(Coin coin) {
        return coin;
    }

    public Coin addCoinToWallet(Coin coin) {
        return coin;
    }
}
