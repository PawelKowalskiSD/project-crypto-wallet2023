package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class CoinService {
    private final CoinRepository coinRepository;


    public Coin findCoinById(Long coinId) throws CoinNotFoundException {
        return coinRepository.findById(coinId).orElseThrow(CoinNotFoundException::new);
    }
}
