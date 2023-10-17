package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    public Role createNewRole(Role role) {
        return role;
    }

    public Role addRoleToUser(Role role) {
        return role;
    }

    public List<Role> getRoles() {
        return new ArrayList<>();
    }

    public Role getRoleById(Long roleId) {
        return new Role();
    }

    public Role removeUserRoles(Role role) {
        return role;
    }
}
