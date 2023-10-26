package com.app.crypto.wallet.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCoin {
    private String coinId;
    private String coinName;
    private String symbol;
    private Long marketCapRank;
}
