package com.app.crypto.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadWalletDto {

    @JsonProperty("id")
    private long walletId;

    @JsonProperty("name")
    private String walletName;

    @JsonProperty("coins")
    private List<ReadCoinDto> coins = new ArrayList<>();

    public ReadWalletDto(long walletId, String walletName) {
        this.walletId = walletId;
        this.walletName = walletName;
    }
}
