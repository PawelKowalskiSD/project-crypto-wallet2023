package com.app.crypto.wallet.client.service;

import com.app.crypto.wallet.client.config.AppConfig;
import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.Search;
import com.app.crypto.wallet.domain.SearchCoin;
import com.app.crypto.wallet.domain.dto.SearchDto;
import com.app.crypto.wallet.repository.CoinRepository;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.service.UserService;
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
    private final AppConfig appConfig;
    private final CoinRepository coinRepository;

    public Search searchCoin(String coin) {
        String apiUrl = "https://api.coingecko.com/api/v3/search?query=" + coin;
        ResponseEntity<SearchDto> response = restTemplate.getForEntity(apiUrl, SearchDto.class);
        return new Search(Objects.requireNonNull(response.getBody()).getCoins().stream()
                .map(sd -> new SearchCoin(sd.getCoinId(), sd.getCoinName(), sd.getSymbol(), sd.getMarketCapRank()))
                .collect(Collectors.toList()));
    }

    public Coin addCoinToWallet(Coin coin) {
        String apiUrl = appConfig.getCoinGeckoBasicUrl() + "/coins/markets?vs_currency=usd&ids=" + coin.getCoinName();
        Coin[] addCoin = restTemplate.getForObject(apiUrl, Coin[].class);
        if (addCoin != null) {
            return coin;
        } else {
            throw new RuntimeException("");
        }

    }
}
