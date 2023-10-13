package com.app.crypto.wallet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDto {
    private String roleName;
    private long userId;
}
