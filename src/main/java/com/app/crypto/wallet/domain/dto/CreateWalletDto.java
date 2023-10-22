package com.app.crypto.wallet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateWalletDto {
    private String walletName;
    private long userId;

    public CreateWalletDto(String walletName) {
        this.walletName = walletName;
    }
}
