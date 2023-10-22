package com.app.crypto.wallet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReadWalletDto {
    private long walletId;
    private String walletName;
    private long userId;
    private List<ReadCoinDto> coins = new ArrayList<>();

    public ReadWalletDto(long walletId, String walletName) {
        this.walletId = walletId;
        this.walletName = walletName;
    }
}
