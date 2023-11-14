package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.client.service.CoinGeckoClientService;
import com.app.crypto.wallet.domain.dto.AddCoinDto;
import com.app.crypto.wallet.domain.dto.ReadCoinDto;
import com.app.crypto.wallet.domain.dto.SearchDto;
import com.app.crypto.wallet.domain.dto.SellCoinDto;
import com.app.crypto.wallet.exceptions.CoinNotFoundException;
import com.app.crypto.wallet.exceptions.CoinQuantityNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.CoinService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
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
    public ResponseEntity<ReadCoinDto> getCoin(@PathVariable Long coinId) throws CoinNotFoundException, UserPermissionsException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadCoinDto(coinService.findCoinById(coinId)));
    }


    @PostMapping(value = "/adds", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadCoinDto> addCoin(@RequestBody AddCoinDto addCoinDto) throws WalletNotFoundException, UserPermissionsException, CoinNotFoundException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadCoinDto(coinGeckoClientService.addCoinToWallet(dtoMapper.mapToCoin(addCoinDto))));
    }

    @PostMapping(value = "/sells", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadCoinDto> sellCoinFromWallet(@RequestBody SellCoinDto sellCoinDto) throws WalletNotFoundException, UserPermissionsException, CoinNotFoundException, CoinQuantityNotFoundException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadCoinDto(coinGeckoClientService.sellCoin(dtoMapper.mapToCoin(sellCoinDto))));
    }
}
