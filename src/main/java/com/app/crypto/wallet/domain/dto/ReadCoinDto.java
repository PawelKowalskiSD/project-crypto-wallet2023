package com.app.crypto.wallet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReadCoinDto {
    private long coinId;
    private String coinName;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal averagePurchasePrice;
    private BigDecimal averageSalePrice;
    private BigDecimal currentPrice;

    public ReadCoinDto(long coinId, String coinName, String symbol, BigDecimal currentPrice) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }
}
