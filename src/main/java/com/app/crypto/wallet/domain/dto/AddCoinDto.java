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
    private String coinName;
    private BigDecimal quantity;
}
