package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.repository.CoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinServiceTest {
    @Mock
    private CoinRepository coinRepository;
    @Mock
    private AuthConfig authConfig;
    @InjectMocks
    private CoinService coinService;

    @Test
    void shouldFindCoinById() throws CoinNotFoundException, UserPermissionsException {
        //Given
        User jan = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));
        Coin bitcoin = new Coin(1L, "Bitcoin", "btc", new BigDecimal(10),
                new BigDecimal(32000), new BigDecimal(32000), null, null, new BigDecimal(320000), new Wallet(1L, "wallet", jan));
        when(coinRepository.findById(bitcoin.getCoinId())).thenReturn(Optional.of(bitcoin));
        when(authConfig.getUserIdFromAuthentication()).thenReturn(bitcoin.getWallet().getUser().getUserId());
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