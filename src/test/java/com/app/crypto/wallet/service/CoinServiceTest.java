package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.repository.CoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinServiceTest {

    @InjectMocks
    private CoinService coinService;

    @Mock
    private CoinRepository coinRepository;

    @Test
    void shouldFindCoinById() throws CoinNotFoundException {
        //Given
        Coin bitcoin = new Coin(1L, "Bitcoin", "btc", new BigDecimal(32000));
        when(coinRepository.findById(bitcoin.getCoinId())).thenReturn(Optional.of(bitcoin));
        //When
        Coin result = coinService.findCoinById(bitcoin.getCoinId());
        //Then
        assertEquals(1L, result.getCoinId());
        assertEquals("Bitcoin", result.getCoinName());
        assertEquals("btc", result.getSymbol());
        assertEquals(bitcoin.getCurrentPrice(), result.getCurrentPrice());
        verify(coinRepository, times(1)).findById(bitcoin.getCoinId());
    }
}