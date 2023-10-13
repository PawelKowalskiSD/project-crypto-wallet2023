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
    private String mailAddressee;
    private boolean isEnabled;
    private String role;
    private List<ReadWalletDto> walletList = new ArrayList<>();

}
