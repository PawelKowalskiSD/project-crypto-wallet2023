package com.app.crypto.wallet.client.service;

import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.Search;
import com.app.crypto.wallet.domain.SearchCoin;
import com.app.crypto.wallet.domain.dto.SearchDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Service
public class CoinGeckoClientService {
    private final RestTemplate restTemplate;

    public Search searchCoin(String coin) {
        String apiUrl = "https://api.coingecko.com/api/v3/search?query=" + coin;
        ResponseEntity<SearchDto> response = restTemplate.getForEntity(apiUrl, SearchDto.class);
        return new Search(Objects.requireNonNull(response.getBody()).getCoins().stream()
                .map(sd -> new SearchCoin(sd.getCoinId(), sd.getCoinName(), sd.getSymbol(), sd.getMarketCapRank()))
                .collect(Collectors.toList()));
    }

    public Coin addCoinToWallet(Coin coin) {
        return coin;
    }
}
