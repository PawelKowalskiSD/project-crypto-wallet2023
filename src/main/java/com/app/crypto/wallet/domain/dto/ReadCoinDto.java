package com.app.crypto.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadCoinDto {

    @JsonProperty("id")
    private long coinId;

    @JsonProperty("name")
    private String coinName;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    @JsonProperty("current_price")
    private BigDecimal currentPrice;

    @JsonProperty("average_purchase_price")
    private BigDecimal averagePurchasePrice;

    @JsonProperty("average_sale_price")
    private BigDecimal averageSalePrice;

    @JsonProperty("total_value_of_coins_sold")
    private BigDecimal totalValueOfCoinsSold;

    @JsonProperty("total_value_purchase_coin")
    private BigDecimal totalValuePurchaseCoin;

    @JsonProperty("wallet_id")
    private long walletId;

    public ReadCoinDto(long coinId, String coinName, String symbol, BigDecimal currentPrice) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }

    public ReadCoinDto(long coinId, String coinName, BigDecimal quantity, BigDecimal currentPrice) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }

    public ReadCoinDto(long coinId, String coinName, String symbol, BigDecimal quantity, BigDecimal currentPrice) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }

    public ReadCoinDto(long coinId, String coinName, String symbol, BigDecimal quantity, BigDecimal currentPrice, BigDecimal averagePurchasePrice, BigDecimal averageSalePrice, BigDecimal totalValueOfCoinsSold, BigDecimal totalValuePurchaseCoin) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.averagePurchasePrice = averagePurchasePrice;
        this.averageSalePrice = averageSalePrice;
        this.totalValueOfCoinsSold = totalValueOfCoinsSold;
        this.totalValuePurchaseCoin = totalValuePurchaseCoin;
    }
}
