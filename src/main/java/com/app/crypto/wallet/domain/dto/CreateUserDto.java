package com.app.crypto.wallet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserDto {
    private String username;
    private String password;
    private String mailAddressee;
    private List<ReadRoleDto> roles;
}
