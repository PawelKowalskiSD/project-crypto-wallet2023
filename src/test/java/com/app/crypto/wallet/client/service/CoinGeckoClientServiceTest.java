package com.app.crypto.wallet.client.service;

import com.app.crypto.wallet.client.config.AppConfig;
import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.ReadCoin;
import com.app.crypto.wallet.domain.Search;
import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.domain.dto.SearchCoinDto;
import com.app.crypto.wallet.domain.dto.SearchDto;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.exceptions.CoinQuantityNotFoundException;
import com.app.crypto.wallet.repository.CoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinGeckoClientServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AppConfig appConfig;

    @Mock
    private CoinRepository coinRepository;

    @InjectMocks
    private CoinGeckoClientService coinGeckoClientService;

    @Test
    void shouldSearchCoin() {
        //Given
        String coinName = "bitcoin";
        String apiUrl = "https://api.coingecko.com/api/v3/search?query=" + coinName;
        SearchDto searchDto = new SearchDto(List.of(new SearchCoinDto("bitcoin", "Bitcoin", "btc", 730002000L)));
        ResponseEntity<SearchDto> coins = new ResponseEntity<>(searchDto, HttpStatusCode.valueOf(200));
        when(restTemplate.getForEntity(apiUrl, SearchDto.class)).thenReturn(coins);
        //When
        Search result = coinGeckoClientService.searchCoin(coinName);
        //Then
        assertEquals(searchDto.getCoins().get(0).getCoinId(), result.getCoins().get(0).getCoinId());
        assertEquals(searchDto.getCoins().get(0).getCoinName(), result.getCoins().get(0).getCoinName());
        assertEquals(searchDto.getCoins().get(0).getSymbol(), result.getCoins().get(0).getSymbol());
        assertEquals(searchDto.getCoins().get(0).getMarketCapRank(), result.getCoins().get(0).getMarketCapRank());
    }

    @Test
    void shouldAddCoinToWalletWhenCoinIs_In_Database() throws CoinNotFoundException {
        //Given
        Coin bitcoinInDatabase = new Coin(1L, "Bitcoin", "btc", new BigDecimal(10),
                new BigDecimal(32000), new BigDecimal(32000), null, null, new BigDecimal(320000), new Wallet(1L, "wallet"));
        String apiUrl = appConfig.getCoinGeckoBasicUrl() + "/coins/markets?vs_currency=usd&ids=" + bitcoinInDatabase.getCoinName();
        when(coinRepository.findCoinsByCoinNameAndWallet_WalletId(bitcoinInDatabase.getCoinName(), bitcoinInDatabase.getWallet().getWalletId())).thenReturn(bitcoinInDatabase);
        ReadCoin[] readCoins = new ReadCoin[]{new ReadCoin("Bitcoin", "btc", new BigDecimal(10),
                new BigDecimal(32000), new BigDecimal(32000), null, null, new BigDecimal(320000), 1L)};
        when(restTemplate.getForObject(apiUrl, ReadCoin[].class)).thenReturn(readCoins);
        //When
        Coin result = coinGeckoClientService.addCoinToWallet(bitcoinInDatabase);
        //Then
        assertEquals(bitcoinInDatabase.getCoinName(), result.getCoinName());
        verify(coinRepository, times(1)).findCoinsByCoinNameAndWallet_WalletId(bitcoinInDatabase.getCoinName(), bitcoinInDatabase.getWallet().getWalletId());
        verify(coinRepository, times(1)).save(bitcoinInDatabase);
    }

    @Test
    void shouldAddCoinToWalletWhenCoinIs_Not_In_Database() throws CoinNotFoundException {
        //Given
        String bitcoin = "Bitcoin";
        long walletId = 1L;
        Coin bitcoinInDatabase = new Coin(1L, "Bitcoin", "btc", new BigDecimal(10),
                new BigDecimal(32000), new BigDecimal(32000), null, null, new BigDecimal(320000), new Wallet(1L, "wallet"));
        String apiUrl = appConfig.getCoinGeckoBasicUrl() + "/coins/markets?vs_currency=usd&ids=" + bitcoin;
        when(coinRepository.findCoinsByCoinNameAndWallet_WalletId(bitcoin, walletId)).thenReturn(null);
        ReadCoin[] readCoins = new ReadCoin[]{new ReadCoin("Bitcoin", "btc", new BigDecimal(10),
                new BigDecimal(32000), new BigDecimal(32000), null, null, new BigDecimal(320000), 1L)};
        when(restTemplate.getForObject(apiUrl, ReadCoin[].class)).thenReturn(readCoins);
        //When
        Coin result = coinGeckoClientService.addCoinToWallet(bitcoinInDatabase);
        //Then
        assertEquals(bitcoinInDatabase.getCoinName(), result.getCoinName());
        verify(coinRepository, times(1)).findCoinsByCoinNameAndWallet_WalletId(bitcoin, walletId);
        verify(coinRepository, times(1)).save(bitcoinInDatabase);
    }

    @Test
    void shouldSellCoinWhenAverageSalePriceIs_Null_AndTotalValueOfCoinsSold_Is_Null() throws CoinNotFoundException, CoinQuantityNotFoundException {
        //Given
        Coin bitcoinInDatabase = new Coin(1L, "Bitcoin", "btc", new BigDecimal(10),
                new BigDecimal(32000), new BigDecimal(32000), null, null, new BigDecimal(320000), new Wallet(1L, "wallet"));
        String apiUrl = appConfig.getCoinGeckoBasicUrl() + "/coins/markets?vs_currency=usd&ids=" + bitcoinInDatabase.getCoinName();
        when(coinRepository.findCoinsByCoinNameAndWallet_WalletId(bitcoinInDatabase.getCoinName(), bitcoinInDatabase.getWallet().getWalletId())).thenReturn(bitcoinInDatabase);
        ReadCoin[] readCoins = new ReadCoin[]{new ReadCoin("Bitcoin", "btc", new BigDecimal(10),
                new BigDecimal(32000), new BigDecimal(32000), null, null, new BigDecimal(320000), 1L)};
        when(restTemplate.getForObject(apiUrl, ReadCoin[].class)).thenReturn(readCoins);
        //When
        Coin result = coinGeckoClientService.sellCoin(bitcoinInDatabase);
        //Then
        assertEquals(bitcoinInDatabase.getCoinName(), result.getCoinName());
        verify(coinRepository, times(1)).findCoinsByCoinNameAndWallet_WalletId(bitcoinInDatabase.getCoinName(), bitcoinInDatabase.getWallet().getWalletId());
        verify(coinRepository, times(1)).save(bitcoinInDatabase);
    }

    @Test
    void shouldSellCoinWhenAverageSalePriceIs_Not_Null_AndTotalValueOfCoinsSoldIs_Not_Null() throws CoinNotFoundException, CoinQuantityNotFoundException {
        //Given
        Coin bitcoinInDatabase = new Coin(1L, "Bitcoin", "btc", new BigDecimal(10),
                new BigDecimal(32000), new BigDecimal(32000), new BigDecimal(32000), new BigDecimal(320000), new BigDecimal(320000), new Wallet(1L, "wallet"));
        String apiUrl = appConfig.getCoinGeckoBasicUrl() + "/coins/markets?vs_currency=usd&ids=" + bitcoinInDatabase.getCoinName();
        when(coinRepository.findCoinsByCoinNameAndWallet_WalletId(bitcoinInDatabase.getCoinName(), bitcoinInDatabase.getWallet().getWalletId())).thenReturn(bitcoinInDatabase);
        ReadCoin[] readCoins = new ReadCoin[]{new ReadCoin("Bitcoin", "btc", new BigDecimal(10),
                new BigDecimal(32000), new BigDecimal(32000), null, null, new BigDecimal(320000), 1L)};
        when(restTemplate.getForObject(apiUrl, ReadCoin[].class)).thenReturn(readCoins);
        //When
        Coin result = coinGeckoClientService.sellCoin(bitcoinInDatabase);
        //Then
        assertEquals(bitcoinInDatabase.getCoinName(), result.getCoinName());
        verify(coinRepository, times(1)).findCoinsByCoinNameAndWallet_WalletId(bitcoinInDatabase.getCoinName(), bitcoinInDatabase.getWallet().getWalletId());
        verify(coinRepository, times(1)).save(bitcoinInDatabase);
    }
}