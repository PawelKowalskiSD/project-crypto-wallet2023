package com.app.crypto.wallet.domain.dto;

import com.app.crypto.wallet.domain.Role;
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
    private List<Role> role;
    private List<ReadWalletDto> walletList = new ArrayList<>();

    @Override
    public String toString() {
        return "ReadUserDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", mailAddressee='" + mailAddressee + '\'' +
                ", isEnabled=" + isEnabled +
                ", role=" + role +
                ", walletList=" + walletList +
                '}';
    }
}
