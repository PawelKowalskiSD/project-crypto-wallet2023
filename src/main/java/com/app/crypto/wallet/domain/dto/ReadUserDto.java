package com.app.crypto.wallet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReadUserDto {
    private long userId;
    private String username;
    private String password;
    private String mailAddressee;
    private boolean isEnabled;
    private String role;
    private List<ReadWalletDto> walletList = new ArrayList<>();

    public ReadUserDto(long userId, String username, String mailAddressee, boolean isEnabled, String role, List<ReadWalletDto> walletList) {
        this.userId = userId;
        this.username = username;
        this.mailAddressee = mailAddressee;
        this.isEnabled = isEnabled;
        this.role = role;
        this.walletList = walletList;
    }
}
