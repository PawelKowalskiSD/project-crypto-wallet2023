package com.app.crypto.wallet.client.service;

import com.app.crypto.wallet.client.config.AppConfig;
import com.app.crypto.wallet.domain.*;
import com.app.crypto.wallet.domain.dto.SearchDto;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.repository.CoinRepository;
import com.app.crypto.wallet.repository.WalletRepository;
import com.app.crypto.wallet.service.WalletService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Service
public class CoinGeckoClientService {
    private final RestTemplate restTemplate;
    private final AppConfig appConfig;
    private final CoinRepository coinRepository;
    private final WalletService walletService;

    public Search searchCoin(String coin) {
        String apiUrl = "https://api.coingecko.com/api/v3/search?query=" + coin;
        ResponseEntity<SearchDto> response = restTemplate.getForEntity(apiUrl, SearchDto.class);
        return new Search(Objects.requireNonNull(response.getBody()).getCoins().stream()
                .map(sd -> new SearchCoin(sd.getCoinId(), sd.getCoinName(), sd.getSymbol(), sd.getMarketCapRank()))
                .collect(Collectors.toList()));
    }

    public Coin addCoinToWallet(Coin coin) {
        String apiUrl = appConfig.getCoinGeckoBasicUrl() + "/coins/markets?vs_currency=usd&ids=" + coin.getCoinName();
        ReadCoin[] addCoin = restTemplate.getForObject(apiUrl, ReadCoin[].class);
        if (addCoin != null && addCoin.length > 0) {
            ReadCoin coin1 = addCoin[0];
            Coin checkInDatabase = coinRepository.findCoinsByCoinNameAndWallet_WalletId(coin.getCoinName(), coin.getWallet().getWalletId());
            if (checkInDatabase != null) {
                checkInDatabase.setQuantity(checkInDatabase.getQuantity().add(coin.getQuantity()));
                checkInDatabase.setCurrentPrice(coin1.getCurrentPrice());
                BigDecimal result = coin.getQuantity().multiply(coin1.getCurrentPrice());
                checkInDatabase.setTotalValuePurchaseCoin(result.add(checkInDatabase.getTotalValuePurchaseCoin()));
                checkInDatabase.setAveragePurchasePrice(checkInDatabase.getAveragePurchasePrice().add(coin1.getCurrentPrice()).divide(new BigDecimal(2), RoundingMode.HALF_DOWN));
                coinRepository.save(checkInDatabase);
                return checkInDatabase;
            } else {
                BigDecimal currentPrice = coin1.getCurrentPrice();
                coin.setSymbol(coin1.getSymbol());
                coin.setCurrentPrice(currentPrice);
                coin.setAveragePurchasePrice(coin1.getCurrentPrice());
                coin.setTotalValuePurchaseCoin(coin.getQuantity().multiply(currentPrice));
                coinRepository.save(coin);
            }
        } else {
            throw new RuntimeException("Coin not exist");
        }
        return coin;
    }

    public Coin sellCoin(Coin sellCoin) {
        String apiUrl = appConfig.getCoinGeckoBasicUrl() + "/coins/markets?vs_currency=usd&ids=" + sellCoin.getCoinName();
        ReadCoin[] sell = restTemplate.getForObject(apiUrl, ReadCoin[].class);
        return sellCoin;
    }
}
