package com.app.crypto.wallet.domain;

import jakarta.persistence.*;
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
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long walletId;
    private String walletName;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(
            targetEntity = Coin.class,
            mappedBy = "wallet",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    private List<Coin> coinList = new ArrayList<>();

    public Wallet(String walletName) {
        this.walletName = walletName;
    }

    public Wallet(long walletId, String walletName) {
        this.walletId = walletId;
        this.walletName = walletName;
    }

    public Wallet(long walletId, String walletName, User user) {
        this.walletId = walletId;
        this.walletName = walletName;
        this.user = user;
    }
}
