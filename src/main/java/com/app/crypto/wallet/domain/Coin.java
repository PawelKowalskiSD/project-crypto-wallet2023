package com.app.crypto.wallet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coinId;
    private String coinName;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal averagePurchasePrice;
    private BigDecimal averageSalePrice;
    private BigDecimal currentPrice;
    private BigDecimal totalValuePurchaseCoin;
    private BigDecimal totalValueOfCoinsSold;
    @ManyToOne
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;

    public Coin(long coinId, String coinName, String symbol, BigDecimal currentPrice) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }

    public Coin(String coinName, BigDecimal quantity, BigDecimal currentPrice) {
        this.coinName = coinName;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }

    public Coin(long coinId, String coinName, BigDecimal quantity, BigDecimal currentPrice) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }

    public Coin(long coinId, String coinName, String symbol, BigDecimal quantity, BigDecimal currentPrice, Wallet wallet) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.wallet = wallet;
    }

    public Coin(String coinName, BigDecimal quantity) {
        this.coinName = coinName;
        this.quantity = quantity;
    }

    public Coin(long coinId, String coinName, String symbol, BigDecimal quantity, BigDecimal currentPrice) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }
}
