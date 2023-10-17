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

    public Coin(String coinName, BigDecimal quantity, BigDecimal averageSalePrice, BigDecimal currentPrice, BigDecimal totalValueOfCoinsSold, Wallet wallet) {
        this.coinName = coinName;
        this.quantity = quantity;
        this.averageSalePrice = averageSalePrice;
        this.currentPrice = currentPrice;
        this.totalValueOfCoinsSold = totalValueOfCoinsSold;
        this.wallet = wallet;
    }

    public Coin(String coinName) {
        this.coinName = coinName;
    }

    public Coin(String coinName, BigDecimal quantity, Wallet wallet) {
        this.coinName = coinName;
        this.quantity = quantity;
        this.wallet = wallet;
    }
}
