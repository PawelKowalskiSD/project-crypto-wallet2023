package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.CreateWalletDto;
import com.app.crypto.wallet.domain.dto.EditWalletDto;
import com.app.crypto.wallet.domain.dto.ReadWalletDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/wallets")
@RestController
public class WalletController {
    private final WalletService walletService;
    private final DtoMapper dtoMapper;

    @GetMapping
    public List<ReadWalletDto> getAllWallets() {
        return dtoMapper.mapReadWalletDtoToWalletLists(walletService.findAllWallets());
    }

    @GetMapping(value = "/{walletId}")
    public ReadWalletDto getWallet(@PathVariable Long walletId) {
        return dtoMapper.mapReadWalletDtoToWallet(walletService.findWallet(walletId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createWallet(@RequestBody CreateWalletDto createWalletDto) {
        walletService.createNewWallet(dtoMapper.mapCreateWalletDtoToWallet(createWalletDto));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EditWalletDto editWallet(@RequestBody EditWalletDto editWalletDto) {
        return dtoMapper.mapWalletToEditWalletDto(walletService.editWallet(dtoMapper.mapEditWallDtoToWallet(editWalletDto)));
    }

    @DeleteMapping(value = "/{walletId}")
    public void deleteWallet(@PathVariable Long walletId) {
        walletService.deleteWalletByWalletId(walletId);
    }
}
