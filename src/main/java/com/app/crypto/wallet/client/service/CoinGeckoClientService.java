package com.app.crypto.wallet.client.service;

import com.app.crypto.wallet.domain.AddCoin;
import com.app.crypto.wallet.domain.SearchCoin;
import org.springframework.stereotype.Service;

@Service
public class CoinGeckoClientService {
    public SearchCoin searchCoin(SearchCoin coin) {
        return null;
    }

    public AddCoin addCoinToWallet(AddCoin addCoin, Long walletId) {
        return addCoin;
    }
}
