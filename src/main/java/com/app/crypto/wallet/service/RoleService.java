package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.exceptions.RoleIsAlreadyRemoveException;
import com.app.crypto.wallet.exceptions.RoleIsAssignedException;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.repository.RoleRepository;
import com.app.crypto.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public Role addRoleToUser(Role role) throws RoleNotFoundException, RoleIsAssignedException {
        Role fetchedRole = findByRoleName(role.getRoleName());
        List<User> users = role.getUsers();
        boolean roleAlreadyAssigned = users.stream().map(User::getRoles).anyMatch(u -> u.contains(fetchedRole));
        if (roleAlreadyAssigned) {
            throw new RoleIsAssignedException();
        }
        users.stream()
                .filter(u -> u.getRoles().add(fetchedRole))
                .collect(Collectors.toList());
        userRepository.saveAll(users);
        return role;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long roleId) throws RoleNotFoundException {
        return roleRepository.findByRoleId(roleId).orElseThrow(RoleNotFoundException::new);
    }

    public Role findByRoleName(String name) throws RoleNotFoundException {
        return roleRepository.findByRoleName(name).orElseThrow(RoleNotFoundException::new);
    }

    public Role removeUserRoles(Role role) throws RoleNotFoundException, RoleIsAlreadyRemoveException {
        Role fetchedRole = findByRoleName(role.getRoleName());
        List<User> users = role.getUsers();
        boolean roleIsAlreadyRemove = users.stream().map(User::getRoles).anyMatch(u -> !u.contains(fetchedRole));
        if (roleIsAlreadyRemove) {
            throw new RoleIsAlreadyRemoveException();
        }
        users.stream().filter(u -> u.getRoles().remove(fetchedRole)).collect(Collectors.toList());
        userRepository.saveAll(users);
        return role;
    }

}
