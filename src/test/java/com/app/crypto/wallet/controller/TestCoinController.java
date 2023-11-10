package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.client.service.CoinGeckoClientService;
import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.Search;
import com.app.crypto.wallet.domain.SearchCoin;
import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.domain.dto.*;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.exceptions.CoinQuantityNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.CoinService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestCoinController {
    private final CoinService service = mock(CoinService.class);
    private final DtoMapper dto = mock(DtoMapper.class);
    private final CoinGeckoClientService clientService = mock(CoinGeckoClientService.class);

    @Test
    void shouldSearchCoin() {
        //Given
        CoinController coinController = new CoinController(service, dto, clientService);
        String requestCoinName = "Bitcoin";
        List<SearchCoin> coinsFromApi = new ArrayList<>();
        coinsFromApi.add(new SearchCoin("bitcoin", "Bitcoin", "BTC", 1L));
        coinsFromApi.add(new SearchCoin("bitcoin-cash", "Bitcoin Cash", "BCH", 17L));
        coinsFromApi.add(new SearchCoin("bitcoin-gold", "Bitcoin Gold", "BTG", 123L));
        coinsFromApi.add(new SearchCoin("evmos", "Evmos", "EVM", 1233L));
        Search getCoinsFromApi = new Search(coinsFromApi);
        when(clientService.searchCoin(requestCoinName)).thenReturn(getCoinsFromApi);
        List<SearchCoinDto> coinsDtoFromApi = new ArrayList<>();
        coinsDtoFromApi.add(new SearchCoinDto("bitcoin", "Bitcoin", "BTC", 1L));
        coinsDtoFromApi.add(new SearchCoinDto("bitcoin-cash", "Bitcoin Cash", "BCH", 17L));
        coinsDtoFromApi.add(new SearchCoinDto("bitcoin-gold", "Bitcoin Gold", "BTG", 123L));
        SearchDto responseCoinsDtoFromApi = new SearchDto(coinsDtoFromApi);
        when(dto.mapToSearchDto(getCoinsFromApi)).thenReturn(responseCoinsDtoFromApi);
        //When
        SearchDto result = coinController.searchCoin(requestCoinName).getBody();
        //Then
        assertEquals(3, result.getCoins().size());
        verify(clientService, times(1)).searchCoin(requestCoinName);
    }

    @Test
    void shouldGetCoin() throws CoinNotFoundException {
        //Given
        CoinController coinController = new CoinController(service, dto, clientService);
        long requestCoinId = 1L;
        Coin coinInDatabase = new Coin(1L, "bitcoin", "btc", new BigDecimal(30000));
        when(service.findCoinById(requestCoinId)).thenReturn(coinInDatabase);
        ReadCoinDto responseReadCoinDto = new ReadCoinDto(1L, "bitcoin", "btc", new BigDecimal(30000));
        when(dto.mapToReadCoinDto(coinInDatabase)).thenReturn(responseReadCoinDto);
        //When
        ReadCoinDto result = coinController.getCoin(requestCoinId).getBody();
        //Then
        assertEquals("bitcoin", result.getCoinName());
        assertEquals("btc", result.getSymbol());
        verify(service, times(1)).findCoinById(requestCoinId);
    }

    @Test
    void shouldAddCoin() throws WalletNotFoundException, UserPermissionsException, CoinNotFoundException {
        //Given
        CoinController coinController = new CoinController(service, dto, clientService);
        Wallet walletInDatabase = new Wallet(1L, "first-Wallet");
        AddCoinDto requestAddCoinDto = new AddCoinDto("Bitcoin", new BigDecimal(10), 1L);
        Coin expectedCoinInDatabase = new Coin(1L, "Bitcoin", "BTC", new BigDecimal(10), new BigDecimal(32_000), walletInDatabase);
        when(dto.mapToCoin(requestAddCoinDto)).thenReturn(expectedCoinInDatabase);
        Coin databaseCoin = new Coin(1L, "Bitcoin", "BTC", new BigDecimal(10), new BigDecimal(32_000), null, new BigDecimal(32_000), new BigDecimal(320_000), null, walletInDatabase);
        when(clientService.addCoinToWallet(expectedCoinInDatabase)).thenReturn(databaseCoin);
        ReadCoinDto responseAddCoinDto = new ReadCoinDto(1L, "Bitcoin", "BTC", new BigDecimal(10), new BigDecimal(32_000), null, new BigDecimal(32_000), new BigDecimal(320_000), null, walletInDatabase.getWalletId());
        when(dto.mapToReadCoinDto(databaseCoin)).thenReturn(responseAddCoinDto);
        //When
        ReadCoinDto result = coinController.addCoin(requestAddCoinDto).getBody();
        //Then
        assertEquals(new BigDecimal(10), result.getQuantity());
        assertEquals("Bitcoin", result.getCoinName());
        assertEquals(1L, result.getCoinId());
        assertEquals(1L, result.getWalletId());
        verify(clientService, times(1)).addCoinToWallet(expectedCoinInDatabase);
    }

    @Test
    void shouldSellCoinFromWallet() throws WalletNotFoundException, UserPermissionsException, CoinNotFoundException, CoinQuantityNotFoundException {
        //Given
        CoinController coinController = new CoinController(service, dto, clientService);
        SellCoinDto requestSellCoinDto = new SellCoinDto("bitcoin", new BigDecimal(1), 1L);
        Coin coinInDatabase = new Coin(1L, "bitcoin", new BigDecimal(10), new BigDecimal(32000));
        when(dto.mapToCoin(requestSellCoinDto)).thenReturn(coinInDatabase);
        Coin modifiedCoinInDatabase = new Coin(1L, "bitcoin", new BigDecimal(9), new BigDecimal(32000));
        when(clientService.sellCoin(coinInDatabase)).thenReturn(modifiedCoinInDatabase);
        ReadCoinDto responseCoinDto = new ReadCoinDto(1L, "bitcoin", new BigDecimal(9), new BigDecimal(32000));
        when(dto.mapToReadCoinDto(modifiedCoinInDatabase)).thenReturn(responseCoinDto);
        //When
        ReadCoinDto result = coinController.sellCoinFromWallet(requestSellCoinDto).getBody();
        //Then
        assertEquals(new BigDecimal(9), result.getQuantity());
        verify(clientService, times(1)).sellCoin(coinInDatabase);
    }
}
