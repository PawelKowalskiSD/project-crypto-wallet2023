package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.client.service.CoinGeckoClientService;
import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.dto.ReadCoinDto;
import com.app.crypto.wallet.domain.dto.SellCoinDto;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.CoinService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestCoinController {
    private final CoinService service = mock(CoinService.class);
    private final DtoMapper dto = mock(DtoMapper.class);
    private final CoinGeckoClientService clientService = mock(CoinGeckoClientService.class);

    @Test
    void shouldSearchCoin() {
        //Given
        //When
        //Then
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
        verify(service, times(1)).findCoinById(requestCoinId);
    }

    @Test
    void shouldSellCoinFromWallet() throws WalletNotFoundException {
        //Given
        CoinController coinController = new CoinController(service, dto, clientService);
        SellCoinDto requestSellCoinDto = new SellCoinDto("bitcoin", new BigDecimal(1), new BigDecimal(32000));
        Coin coinInDatabase = new Coin("bitcoin", new BigDecimal(10), new BigDecimal(32000));
        when(dto.mapToCoin(requestSellCoinDto)).thenReturn(coinInDatabase);
        //When
        //Then
    }
}
