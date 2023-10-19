package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Role createNewRole(Role role) {
        return role;
    }

    public Role addRoleToUser(Role role) {
        return role;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long roleId) throws RoleNotFoundException {
        return roleRepository.findByRoleId(roleId).orElseThrow(RoleNotFoundException::new);
    }

    public Role removeUserRoles(Role role) {
        return role;
    }
}
