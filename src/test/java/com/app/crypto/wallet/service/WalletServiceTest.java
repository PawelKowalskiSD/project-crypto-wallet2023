package com.app.crypto.wallet.service;

import com.app.crypto.wallet.client.config.AuthConfig;
import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletService walletService;

    @Test
    void shouldFindAllWallets() throws UserPermissionsException {
        //Given
        User jan = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));

        when(authConfig.getUserIdFromAuthentication()).thenReturn(jan.getUserId());
        List<Wallet> responseWallets = List.of(
                new Wallet(1L, "new wallet", jan),
                new Wallet(2L, "second wallet", jan)
        );
        when(walletRepository.findWalletsByUser_UserId(jan.getUserId())).thenReturn(responseWallets);
        //When
        List<Wallet> result = walletService.findAllWallets();
        //Then
        assertEquals(2,result.size());
        assertEquals("second wallet",result.get(1).getWalletName());
        assertNotEquals(3, result.size());
        assertEquals(1L, result.get(0).getWalletId());
        verify(walletRepository, times(1)).findWalletsByUser_UserId(jan.getUserId());
    }

    @Test
    void shouldFindWallet() throws WalletNotFoundException, UserPermissionsException {
        //Given
        User jan = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));
        User domingo = new User(2L, "domingo", "domingo123", "domingo@wp.pl", true, List.of(new Role(1L,"USER")));
        when(authConfig.getUserIdFromAuthentication()).thenReturn(jan.getUserId());
        Wallet wallet = new Wallet(1L, "new wallet", jan);
        Wallet wallet3 = new Wallet(3L, "new wallet", domingo);
        when(walletRepository.findByWalletId(wallet.getWalletId())).thenReturn(Optional.of(wallet));
        //When
        Wallet result = walletService.findWallet(wallet.getWalletId());
        //Then
        assertEquals("new wallet", result.getWalletName());
        assertEquals(1L, result.getWalletId());
        assertEquals(1L, result.getUser().getUserId());
        assertThrows(WalletNotFoundException.class, () -> walletService.findWallet(2L));
        verify(walletRepository, times(1)).findByWalletId(wallet.getWalletId());
    }

    @Test
    void shouldCreateNewWallet() throws UserPermissionsException, UserNotFoundException {
        //Given
        User jan = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));
        when(authConfig.getUserIdFromAuthentication()).thenReturn(jan.getUserId());
        Wallet createWallet = new Wallet(1L, "new Wallet", jan);
        when(userRepository.findByUserId(jan.getUserId())).thenReturn(Optional.of(jan));
        //When
        Wallet result = walletService.createNewWallet(createWallet);
        //Then
        assertEquals("new Wallet", result.getWalletName());
        assertEquals(1L, result.getWalletId());
        assertThrows(WalletNotFoundException.class, () -> walletService.findWallet(2L));
        verify(walletRepository, times(1)).save(createWallet);
    }

    @Test
    void shouldEditWallet() throws UserPermissionsException {
        //Given
        User jan = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));
        when(authConfig.getUserIdFromAuthentication()).thenReturn(jan.getUserId());
        Wallet walletToEdit = new Wallet(1L, "old wallet", jan);
        when(walletRepository.findByWalletId(walletToEdit.getWalletId())).thenReturn(Optional.of(walletToEdit));
        //When
        Wallet result = walletService.editWallet(walletToEdit);
        //Then
        assertEquals(1L, result.getWalletId());
        assertEquals("old wallet", result.getWalletName());
        assertThrows(WalletNotFoundException.class, () -> walletService.findWallet(2L));
        verify(walletRepository, times(1)).findByWalletId(walletToEdit.getWalletId());
    }

    @Test
    void shouldDeleteWallet() throws UserPermissionsException {
        //Given
        User jan = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));
        when(authConfig.getUserIdFromAuthentication()).thenReturn(jan.getUserId());
        Wallet deleteWallet = new Wallet(1L, "old wallet", jan);
        when(walletRepository.findByWalletId(deleteWallet.getWalletId())).thenReturn(Optional.of(deleteWallet));
        //When
        walletService.deleteWalletByWalletId(deleteWallet.getWalletId());
        //Then
        verify(walletRepository, times(1)).deleteById(deleteWallet.getWalletId());
    }
}
