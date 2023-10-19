package com.app.crypto.wallet.client.service;

import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.dto.SearchCoinDto;
import com.app.crypto.wallet.domain.dto.SearchDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Service
public class CoinGeckoClientService {
    private final RestTemplate restTemplate;
    private final DtoMapper dtoMapper;

    public Coin searchCoin(Coin coin) {
        String apiUrl = "https://api.coingecko.com/api/v3/search?query=" + coin.getCoinName();
        ResponseEntity<SearchDto> response = restTemplate.getForEntity(apiUrl, SearchDto.class);
        System.out.println(response.getBody());
        return dtoMapper.mapToCoins(response.getBody());
    }

    public Coin addCoinToWallet(Coin coin) {
        return coin;
    }
}
