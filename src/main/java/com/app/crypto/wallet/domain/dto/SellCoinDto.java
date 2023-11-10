package com.app.crypto.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellCoinDto {

    @JsonProperty("name")
    private String coinName;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    @JsonProperty("wallet_id")
    private long walletId;

}
