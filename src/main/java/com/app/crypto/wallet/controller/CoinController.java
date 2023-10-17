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

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public SearchCoinDto searchCoin(@RequestBody SearchCoinDto searchCoinDto) {
        return dtoMapper.mapToSearchCoinDto(coinGeckoClientService.searchCoin(dtoMapper.mapToCoin(searchCoinDto)));
    }

    @GetMapping(value = "/{coinId}")
    public ReadCoinDto getCoin(@PathVariable Long coinId) {
        return dtoMapper.mapToReadCoinDto(coinService.findCoinById(coinId));
    }


    @PostMapping(value = "/adds", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddCoinDto addCoin(@RequestBody AddCoinDto addCoinDto) {
        return dtoMapper.mapToAddCoinDto(coinGeckoClientService.addCoinToWallet(dtoMapper.mapToCoin(addCoinDto)));
    }

    @PostMapping(value = "/sells", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SellCoinDto sellCoinFromWallet(@RequestBody SellCoinDto sellCoinDto) {
        return dtoMapper.mapToSellCoinDto(coinService.sellCoin(dtoMapper.mapToCoin(sellCoinDto)));
    }
}
