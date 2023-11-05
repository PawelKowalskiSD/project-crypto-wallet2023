package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.exceptions.RoleIsAlreadyRemoveException;
import com.app.crypto.wallet.exceptions.RoleIsAssignedException;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void shouldAddRoleToUser() throws RoleNotFoundException, RoleIsAssignedException {
        //Given
        Role addRole = new Role(1L, "USER");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(addRole));
        //When
        Role result = roleService.addRoleToUser(addRole);
        //Then
        assertEquals("USER", result.getRoleName());
        assertEquals(1L, result.getRoleId());
        verify(roleRepository, times(1)).findByRoleName(addRole.getRoleName());
    }

    @Test
    void shouldGetRoles() {
        //Given
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "USER"));
        roles.add(new Role(2L, "ADMIN"));
        when(roleRepository.findAll()).thenReturn(roles);
        //When
        List<Role> result = roleService.getRoles();
        //Then
        assertEquals(1L, result.get(0).getRoleId());
        assertEquals("USER", result.get(0).getRoleName());
        assertEquals(2L, result.get(1).getRoleId());
        assertEquals("ADMIN", result.get(1).getRoleName());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void shouldGetRoleById() throws RoleNotFoundException {
        //Given
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "USER"));
        roles.add(new Role(2L, "ADMIN"));
        when(roleRepository.findByRoleId(roles.get(0).getRoleId())).thenReturn(Optional.of(roles.get(0)));
        //When
        Role result = roleService.getRoleById(roles.get(0).getRoleId());
        //Then
        assertEquals(1L, result.getRoleId());
        assertEquals("USER", result.getRoleName());
        assertThrows(RoleNotFoundException.class, () -> roleService.getRoleById(2L));
        verify(roleRepository, times(1)).findByRoleId(roles.get(0).getRoleId());
    }

    @Test
    void shouldFindByRoleName() throws RoleNotFoundException {
        //Given
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "USER"));
        roles.add(new Role(2L, "ADMIN"));
        when(roleRepository.findByRoleName(roles.get(0).getRoleName())).thenReturn(Optional.of(roles.get(0)));
        //When
        Role result = roleService.findByRoleName(roles.get(0).getRoleName());
        //Then
        assertEquals(1L, result.getRoleId());
        assertEquals("USER", result.getRoleName());
        assertThrows(RoleNotFoundException.class, () -> roleService.findByRoleName("USER2"));
        verify(roleRepository, times(1)).findByRoleName(roles.get(0).getRoleName());
    }

    @Test
    void shouldRemoveUserRoles() throws RoleNotFoundException, RoleIsAlreadyRemoveException {
        //Given
        Role removeRole = new Role(1L, "USER");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(removeRole));
        //When
        Role result = roleService.removeUserRoles(removeRole);
        //Then
        assertEquals("USER", result.getRoleName());
        assertEquals(1L, result.getRoleId());
        verify(roleRepository, times(1)).findByRoleName(removeRole.getRoleName());
    }
}