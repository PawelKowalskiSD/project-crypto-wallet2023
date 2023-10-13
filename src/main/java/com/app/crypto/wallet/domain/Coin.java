package com.app.crypto.wallet.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Coin {
    private long coinId;
    private String coinName;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal averagePurchasePrice;
    private BigDecimal averageSalePrice;
    private BigDecimal currentPrice;
    private BigDecimal totalValuePurchaseCoin;
    private BigDecimal totalValueOfCoinsSold;
    private Wallet wallet;
    private User user;

    public Coin(String coinName, BigDecimal quantity, BigDecimal averageSalePrice, BigDecimal currentPrice, BigDecimal totalValueOfCoinsSold, Wallet wallet, User user) {
        this.coinName = coinName;
        this.quantity = quantity;
        this.averageSalePrice = averageSalePrice;
        this.currentPrice = currentPrice;
        this.totalValueOfCoinsSold = totalValueOfCoinsSold;
        this.wallet = wallet;
        this.user = user;
    }

    public Coin(String coinName) {
        this.coinName = coinName;
    }

    public Coin(String coinName, BigDecimal quantity, Wallet wallet, User user) {
        this.coinName = coinName;
        this.quantity = quantity;
        this.wallet = wallet;
        this.user = user;
    }
}
