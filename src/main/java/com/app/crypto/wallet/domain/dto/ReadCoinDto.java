package com.app.crypto.wallet.domain.dto;

import com.app.crypto.wallet.domain.Wallet;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReadCoinDto {
    private long coinId;
    private String coinName;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal averagePurchasePrice;
    private BigDecimal averageSalePrice;
    private BigDecimal currentPrice;
}
