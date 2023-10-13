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
    private BigDecimal quantity;
    private long walletId;
    private long userId;
}
