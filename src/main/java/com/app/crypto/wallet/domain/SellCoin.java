package com.app.crypto.wallet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellCoin {
    private String coinName;
    private BigDecimal quantity;
    private BigDecimal averageSalePrice;
    private BigDecimal currentPrice;
    private long walletId;
    private BigDecimal totalValueOfCoinsSold;
}
