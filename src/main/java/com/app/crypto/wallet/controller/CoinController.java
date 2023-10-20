package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.client.service.CoinGeckoClientService;
import com.app.crypto.wallet.domain.dto.*;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coins")
public class CoinController {
    private final CoinService coinService;
    private final DtoMapper dtoMapper;
    private final CoinGeckoClientService coinGeckoClientService;

    @GetMapping(value = "/search/{coin}")
    public ResponseEntity<SearchDto> searchCoin(@PathVariable String coin) {
        return ResponseEntity.ok(dtoMapper.mapToSearchDto(coinGeckoClientService.searchCoin(coin)));
    }

    @GetMapping(value = "/{coinId}")
    public ResponseEntity<ReadCoinDto> getCoin(@PathVariable Long coinId) throws CoinNotFoundException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadCoinDto(coinService.findCoinById(coinId)));
    }


    @PostMapping(value = "/adds", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddCoinDto> addCoin(@RequestBody AddCoinDto addCoinDto) throws WalletNotFoundException {
        return ResponseEntity.ok().body(dtoMapper.mapToAddCoinDto(coinGeckoClientService.addCoinToWallet(dtoMapper.mapToCoin(addCoinDto))));
    }

    @PostMapping(value = "/sells", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SellCoinDto> sellCoinFromWallet(@RequestBody SellCoinDto sellCoinDto) throws WalletNotFoundException {
        return ResponseEntity.ok().body(dtoMapper.mapToSellCoinDto(coinService.sellCoin(dtoMapper.mapToCoin(sellCoinDto))));
    }
}
