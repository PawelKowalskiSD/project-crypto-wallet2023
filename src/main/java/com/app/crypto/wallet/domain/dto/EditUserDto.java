package com.app.crypto.wallet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EditUserDto {

    private long userId;
    private String username;
    private String password;
    private String mailAddressee;

    public EditUserDto(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "EditUserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mailAddressee='" + mailAddressee + '\'' +
                '}';
    }
}
