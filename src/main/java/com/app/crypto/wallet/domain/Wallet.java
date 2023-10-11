package com.app.crypto.wallet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet {
    private long walletId;
    private String walletName;
    private User user;
    private List<Coin> coinList = new ArrayList<>();

    public Wallet(String walletName) {
        this.walletName = walletName;
    }
}
