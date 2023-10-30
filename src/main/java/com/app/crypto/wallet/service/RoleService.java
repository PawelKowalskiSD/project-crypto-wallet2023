package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.exceptions.RoleIsAssignedException;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.repository.RoleRepository;
import com.app.crypto.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public Role addRoleToUser(Role role) throws RoleIsAssignedException, UserNotFoundException, RoleNotFoundException {
    Role fetchedRole = roleRepository.findByRoleName(role.getRoleName()).orElseThrow(RoleNotFoundException::new);
        for (User user : role.getUsers()) {
            User fetchedUser = userRepository.findByUserId(user.getUserId())
                    .orElseThrow(UserNotFoundException::new);
            boolean roleAlreadyExists = fetchedUser.getRoles().stream()
                    .anyMatch(existingRole -> existingRole.getRoleName().equals(fetchedRole.getRoleName()));

            if (!roleAlreadyExists) {
                fetchedUser.getRoles().add(fetchedRole);
            } else {
                throw new RoleIsAssignedException();
            }
        }
        userRepository.saveAll(role.getUsers());
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

    public Role removeUserRoles(Role role) throws RoleNotFoundException, UserNotFoundException {
        Role fetchedRole = roleRepository.findByRoleName(role.getRoleName()).orElseThrow(RoleNotFoundException::new);
        for (User user : role.getUsers()) {
            User fetchedUser = userRepository.findByUserId(user.getUserId())
                    .orElseThrow(UserNotFoundException::new);
            boolean roleAlreadyExists = fetchedUser.getRoles().stream()
                    .anyMatch(existingRole -> existingRole.getRoleName().equals(fetchedRole.getRoleName()));

            if (roleAlreadyExists) {
                fetchedUser.getRoles().remove(fetchedRole);
            } else {
                throw new RoleNotFoundException();
            }
        }
        userRepository.saveAll(role.getUsers());
        return role;
    }

}
