package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class CoinService {
    private final CoinRepository coinRepository;
    private final AuthConfig authConfig;


    public Coin findCoinById(Long coinId) throws CoinNotFoundException, UserPermissionsException {
        Coin coin = coinRepository.findById(coinId).orElseThrow(CoinNotFoundException::new);
        long validateId = authConfig.getUserIdFromAuthentication();
        if (coin.getWallet().getUser().getUserId() == validateId)
            return coin;
        else
            throw new UserPermissionsException();
    }
}
