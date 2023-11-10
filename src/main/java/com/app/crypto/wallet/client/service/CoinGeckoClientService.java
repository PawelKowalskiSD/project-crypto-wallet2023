package com.app.crypto.wallet.client.service;

import com.app.crypto.wallet.client.config.AppConfig;
import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.ReadCoin;
import com.app.crypto.wallet.domain.Search;
import com.app.crypto.wallet.domain.SearchCoin;
import com.app.crypto.wallet.domain.dto.SearchDto;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.exceptions.CoinQuantityNotFoundException;
import com.app.crypto.wallet.repository.CoinRepository;
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

    public Search searchCoin(String coin) {
        String apiUrl = "https://api.coingecko.com/api/v3/search?query=" + coin;
        ResponseEntity<SearchDto> response = restTemplate.getForEntity(apiUrl, SearchDto.class);
        return new Search(Objects.requireNonNull(response.getBody()).getCoins().stream()
                .map(sd -> new SearchCoin(sd.getCoinId(), sd.getCoinName(), sd.getSymbol(), sd.getMarketCapRank()))
                .collect(Collectors.toList()));
    }

    public Coin addCoinToWallet(Coin coin) throws CoinNotFoundException {
        String apiUrl = appConfig.getCoinGeckoBasicUrl() + "/coins/markets?vs_currency=usd&ids=" + coin.getCoinName();
        ReadCoin[] addCoin = restTemplate.getForObject(apiUrl, ReadCoin[].class);
        if (addCoin != null && addCoin.length > 0) {
            ReadCoin coin1 = addCoin[0];
            Coin checkInDatabase = coinRepository.findCoinsByCoinNameAndWallet_WalletId(coin.getCoinName(), coin.getWallet().getWalletId());
            if (checkInDatabase != null) {
                BigDecimal coinQuantity = coin.getQuantity();
                BigDecimal coin1CurrentPrice = coin1.getCurrentPrice();
                BigDecimal totalValuePurchaseCoin = checkInDatabase.getTotalValuePurchaseCoin();
                BigDecimal result = coinQuantity.multiply(coin1CurrentPrice);
                checkInDatabase.setQuantity(checkInDatabase.getQuantity().add(coinQuantity));
                checkInDatabase.setCurrentPrice(coin1CurrentPrice);
                checkInDatabase.setTotalValuePurchaseCoin(result.add(totalValuePurchaseCoin));
                checkInDatabase.setAveragePurchasePrice(checkInDatabase.getAveragePurchasePrice().add(coin1CurrentPrice).divide(new BigDecimal(2), RoundingMode.HALF_DOWN));
                coinRepository.save(checkInDatabase);
                return checkInDatabase;
            } else {
                BigDecimal currentPrice = coin1.getCurrentPrice();
                coin.setSymbol(coin1.getSymbol());
                coin.setCurrentPrice(currentPrice);
                coin.setAveragePurchasePrice(coin1.getCurrentPrice());
                coin.setTotalValuePurchaseCoin(coin.getQuantity().multiply(currentPrice));
                coinRepository.save(coin);
                return coin;
            }
        } else {
            throw new CoinNotFoundException();
        }
    }

    public Coin sellCoin(Coin sellCoin) throws CoinNotFoundException, CoinQuantityNotFoundException {
        String apiUrl = appConfig.getCoinGeckoBasicUrl() + "/coins/markets?vs_currency=usd&ids=" + sellCoin.getCoinName();
        ReadCoin[] sell = restTemplate.getForObject(apiUrl, ReadCoin[].class);
            if (sell != null && sell.length > 0) {
                ReadCoin readCoin = sell[0];
                Coin checkInDatabase = coinRepository.findCoinsByCoinNameAndWallet_WalletId(sellCoin.getCoinName(), sellCoin.getWallet().getWalletId());
                if (checkInDatabase != null && checkInDatabase.getQuantity().compareTo(sellCoin.getQuantity()) >= 0) {
                    checkInDatabase.setQuantity(checkInDatabase.getQuantity().subtract(sellCoin.getQuantity()));
                    checkInDatabase.setCurrentPrice(readCoin.getCurrentPrice());
                    if (checkInDatabase.getTotalValueOfCoinsSold() == null)
                        checkInDatabase.setTotalValueOfCoinsSold(sellCoin.getQuantity().multiply(readCoin.getCurrentPrice()));
                    else
                        checkInDatabase.setTotalValueOfCoinsSold(sellCoin.getQuantity().multiply(readCoin.getCurrentPrice()).add(checkInDatabase.getTotalValueOfCoinsSold()));
                    if (checkInDatabase.getAverageSalePrice() == null)
                        checkInDatabase.setAverageSalePrice(readCoin.getCurrentPrice());
                    else
                        checkInDatabase.setAverageSalePrice(checkInDatabase.getAverageSalePrice().add(readCoin.getCurrentPrice()).divide(new BigDecimal(2), RoundingMode.HALF_DOWN));
                    coinRepository.save(checkInDatabase);
                    return checkInDatabase;
                } else
                    throw new CoinQuantityNotFoundException();
            }
        throw new CoinNotFoundException();
    }
}
