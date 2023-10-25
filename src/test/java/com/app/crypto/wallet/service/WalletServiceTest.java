package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private AuthConfig authConfig;

    @InjectMocks
    private WalletService walletService;

    @Test
    void findAllWallets() throws UserPermissionsException {
        //Given
        List<Wallet> wallets = List.of(
                new Wallet(1L, "new wallet", new User(1L)),
                new Wallet(2L, "second wallet", new User(1L)),
                new Wallet(3L, "My wallet", new User(2L))
        );
        when(walletRepository.findAll()).thenReturn(wallets);
        //When
        List<Wallet> result = walletService.findAllWallets();
        //Then
        assertEquals(2,result.size());
        assertEquals("second wallet",result.get(1).getWalletName());
        assertNotEquals(3, result.size());
        assertEquals(1L, result.get(0).getWalletId());
        verify(walletRepository, times(1)).findAll();
    }

    @Test
    void findWallet() throws WalletNotFoundException {
        //Given
        Wallet wallet = new Wallet(1L, "new wallet", new User(1L));
        when(walletRepository.findWalletByWalletIdAndUser_UserId(wallet.getWalletId(), wallet.getUser().getUserId())).thenReturn(Optional.of(wallet));
        //When
        Wallet result = walletService.findWallet(wallet.getWalletId());
        //Then
        assertEquals("new wallet", result.getWalletName());
    }

    @Test
    void createNewWallet() {
        //Given

        //When

        //Then
    }
}
