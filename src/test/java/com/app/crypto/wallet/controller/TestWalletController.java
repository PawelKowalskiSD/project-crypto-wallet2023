package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.domain.dto.CreateWalletDto;
import com.app.crypto.wallet.domain.dto.EditWalletDto;
import com.app.crypto.wallet.domain.dto.ReadWalletDto;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.WalletService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TestWalletController {
    private final WalletService service = mock(WalletService.class);
    private final DtoMapper dto = mock(DtoMapper.class);

    @Test
    void shouldGetAllWallets() throws UserPermissionsException {
        //Given
        WalletController walletController = new WalletController(service, dto);
        List<Wallet> databaseWallets = new ArrayList<>();
        databaseWallets.add(new Wallet(1L, "first wallet"));
        databaseWallets.add(new Wallet(2L, "second wallet"));
        when(service.findAllWallets()).thenReturn(databaseWallets);
        List<ReadWalletDto> responseWalletsDto = new ArrayList<>();
        responseWalletsDto.add(new ReadWalletDto(1L, "first wallet"));
        responseWalletsDto.add(new ReadWalletDto(2L, "second wallet"));
        when(dto.mapToReadWalletDtoList(databaseWallets)).thenReturn(responseWalletsDto);
        //When
        List<ReadWalletDto> result = walletController.getAllWallets().getBody();
        //Then
        assertEquals(2, result.size());
        verify(service, times(1)).findAllWallets();
    }

    @Test
    void shouldGetWallet() throws WalletNotFoundException {
        //Given
        WalletController walletController = new WalletController(service, dto);
        Wallet databaseWallet = new Wallet(1L, "first wallet");
        long requestWalletId = 1L;
        when(service.findWallet(requestWalletId)).thenReturn(databaseWallet);
        ReadWalletDto responseWalletDto = new ReadWalletDto(1L, "first wallet");
        when(dto.mapToReadWalletDto(databaseWallet)).thenReturn(responseWalletDto);
        //When
        ReadWalletDto result = walletController.getWallet(requestWalletId).getBody();
        //Then
        assertEquals(1L, result.getWalletId());
        verify(service, times(1)).findWallet(requestWalletId);
    }

    @Test
    void shouldCreateWallet() throws UserPermissionsException, UserNotFoundException {
        //Given
        WalletController walletController = new WalletController(service, dto);
        CreateWalletDto createWalletDto = new CreateWalletDto("first wallet");
        Wallet walletToDatabase = new Wallet("first wallet");
        when(dto.mapToWallet(createWalletDto)).thenReturn(walletToDatabase);
        Wallet walletInDatabase = new Wallet(1L, "first wallet");
        when(service.createNewWallet(walletToDatabase)).thenReturn(walletInDatabase);
        ReadWalletDto responseWalletDto = new ReadWalletDto(1L, "first wallet");
        when(dto.mapToReadWalletDto(walletInDatabase)).thenReturn(responseWalletDto);
        //When
        ReadWalletDto result = walletController.createWallet(createWalletDto).getBody();
        //Then
        assertEquals("first wallet", result.getWalletName());
        verify(service, times(1)).createNewWallet(walletToDatabase);
    }

    @Test
    void shouldEditWallet() {
        //Given
        WalletController walletController = new WalletController(service, dto);
        EditWalletDto requestWalletDto = new EditWalletDto("my wallet");
        Wallet walletToModify = new Wallet(1L, "first wallet");
        when(dto.mapToWallet(requestWalletDto)).thenReturn(walletToModify);
        Wallet modifiedWallet = new Wallet(1L, "my wallet");
        when(service.editWallet(walletToModify)).thenReturn(modifiedWallet);
        ReadWalletDto responseWalletDto = new ReadWalletDto(1L, "my wallet");
        when(dto.mapToReadWalletDto(modifiedWallet)).thenReturn(responseWalletDto);
        //When
        ReadWalletDto result = walletController.editWallet(requestWalletDto).getBody();
        //Then
        assertEquals("my wallet", result.getWalletName());
        verify(service, times(1)).editWallet(walletToModify);
    }

    @Test
    void shouldDeleteWallet() {
        //Given
        WalletController walletController = new WalletController(service, dto);
        Wallet walletInDatabase = new Wallet(1L, "first wallet");
        long requestWalletId = 1L;
        //When
        walletController.deleteWallet(requestWalletId);
        //Then
        verify(service, times(1)).deleteWalletByWalletId(walletInDatabase.getWalletId());
    }
}
