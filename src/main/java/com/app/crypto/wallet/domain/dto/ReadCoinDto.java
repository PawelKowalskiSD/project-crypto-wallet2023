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

    @JsonProperty("currentPrice")
    private BigDecimal currentPrice;

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
}
