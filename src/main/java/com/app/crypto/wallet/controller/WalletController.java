package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.CreateWalletDto;
import com.app.crypto.wallet.domain.dto.EditWalletDto;
import com.app.crypto.wallet.domain.dto.ReadWalletDto;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/wallets")
@RestController
public class WalletController {
    private final WalletService walletService;
    private final DtoMapper dtoMapper;

    @GetMapping
    public ResponseEntity<List<ReadWalletDto>> getAllWallets() {
        return ResponseEntity.ok().body(dtoMapper.mapToReadWalletDtoList(walletService.findAllWallets()));
    }

    @GetMapping(value = "/{walletId}")
    public ResponseEntity<ReadWalletDto> getWallet(@PathVariable Long walletId) throws WalletNotFoundException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadWalletDto(walletService.findWallet(walletId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadWalletDto> createWallet(@RequestBody CreateWalletDto createWalletDto) {

        return ResponseEntity.ok().body(dtoMapper.mapToReadWalletDto(walletService.createNewWallet(dtoMapper.mapToWallet(createWalletDto))));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadWalletDto> editWallet(@RequestBody EditWalletDto editWalletDto) {
        return ResponseEntity.ok().body(dtoMapper.mapToReadWalletDto(walletService.editWallet(dtoMapper.mapToWallet(editWalletDto))));
    }

    @DeleteMapping(value = "/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long walletId) {
        walletService.deleteWalletByWalletId(walletId);
        return ResponseEntity.ok().build();
    }
}
