package com.app.crypto.wallet.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReadCoin {
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
}
