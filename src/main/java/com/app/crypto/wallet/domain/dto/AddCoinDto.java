package com.app.crypto.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddCoinDto {

    @JsonProperty("name")
    private String coinName;

    @JsonProperty("quantity")
    private BigDecimal quantity;
}
