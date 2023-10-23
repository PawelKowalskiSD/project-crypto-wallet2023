package com.app.crypto.wallet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddCoinDto {
    private String coinName;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal currentPrice;
    private long walletId;
}
