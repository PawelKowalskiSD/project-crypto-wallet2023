package com.app.crypto.wallet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    private long roleId;
    private String roleName;
    private User user;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, User user) {
        this.roleName = roleName;
        this.user = user;
    }
}
