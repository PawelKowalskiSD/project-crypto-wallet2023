package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.client.service.CoinGeckoClientService;
import com.app.crypto.wallet.domain.dto.AddCoinDto;
import com.app.crypto.wallet.domain.dto.ReadCoinDto;
import com.app.crypto.wallet.domain.dto.SellCoinDto;
import com.app.crypto.wallet.domain.dto.SearchCoinDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coins")
public class CoinController {
    private final CoinService coinService;
    private final DtoMapper dtoMapper;
    private final CoinGeckoClientService coinGeckoClientService;

    @GetMapping
    public SearchCoinDto searchCoin(@RequestBody SearchCoinDto searchCoinDto) {
        return dtoMapper.mapSearchCoinToSearchCoinDto(coinGeckoClientService.searchCoin(dtoMapper.mapSearchCoinDtoToSearchCoin(searchCoinDto)));
    }

    @PostMapping(value = "/{walletId}/add-coins", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddCoinDto addCoin(@PathVariable Long walletId, @RequestBody AddCoinDto addCoinDto) {
        return dtoMapper.mapCoinToAddCoinDto(coinGeckoClientService.addCoinToWallet(dtoMapper.mapAddCoinDtoToCoin(addCoinDto), walletId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public SellCoinDto sellCoinFromWallet(@RequestBody SellCoinDto sellCoinDto) {
        return dtoMapper.mapSellCoinToSellCoinDto(coinService.sellCoin(dtoMapper.mapSellCoinDtoToSellCoin(sellCoinDto)));
    }
}
