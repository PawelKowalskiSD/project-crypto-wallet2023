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
public class User {
    private long userId;
    private String username;
    private String password;
    private String mailAddressee;
    private boolean isEnabled;
    private String role;
    private List<Wallet> walletList = new ArrayList<>();
    private VerificationKey verificationKey;

    public User(String username, String password, String mailAddressee) {
        this.username = username;
        this.password = password;
        this.mailAddressee = mailAddressee;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
