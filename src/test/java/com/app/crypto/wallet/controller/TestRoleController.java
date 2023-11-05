package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.dto.InputDataRoleDto;
import com.app.crypto.wallet.domain.dto.ReadRoleDto;
import com.app.crypto.wallet.exceptions.RoleIsAlreadyRemoveException;
import com.app.crypto.wallet.exceptions.RoleIsAssignedException;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.RoleService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestRoleController {
    private final RoleService roleService = mock(RoleService.class);
    private final DtoMapper dto = mock(DtoMapper.class);

    @Test
    void shouldGetAllRoles() {
        //Given
        RoleController roleController = new RoleController(roleService, dto);
        List<Role> getAllRoles = List.of(new Role(1L, "USER"), new Role(2L, "ADMIN"));
        when(roleService.getRoles()).thenReturn(getAllRoles);
        List<ReadRoleDto> responseReadRoleDto = List.of(new ReadRoleDto(1L, "USER"), new ReadRoleDto(2L, "ADMIN"));
        when(dto.mapToReadRoleDtoList(getAllRoles)).thenReturn(responseReadRoleDto);
        //When
        List<ReadRoleDto> result = roleController.getAllRoles().getBody();
        //Then
        assertEquals(2, result.size());
        verify(roleService, times(1)).getRoles();
    }

    @Test
    void shouldGetRole() throws RoleNotFoundException {
        //Given
        RoleController roleController = new RoleController(roleService, dto);
        long requestRoleId = 1L;
        Role roleInDatabase = new Role(1L, "USER");
        when(roleService.getRoleById(requestRoleId)).thenReturn(roleInDatabase);
        ReadRoleDto responseReadRoleDto = new ReadRoleDto(1L, "USER");
        when(dto.mapToReadRoleDto(roleInDatabase)).thenReturn(responseReadRoleDto);
        //When
        ReadRoleDto result = roleController.getRole(requestRoleId).getBody();
        //Then
        assertEquals("USER", result.getRoleName());
        verify(roleService, times(1)).getRoleById(requestRoleId);
    }

    @Test
    void shouldAddRoleToUser() throws UserNotFoundException, RoleNotFoundException, RoleIsAssignedException {
        //Given
        RoleController roleController = new RoleController(roleService, dto);
        InputDataRoleDto requestInputDataRoleDtoToUser = new InputDataRoleDto("ADMIN", 1L);
        Role userRolesToModify = new Role("USER");
        when(dto.mapToRole(requestInputDataRoleDtoToUser)).thenReturn(userRolesToModify);
        Role modifiedUserRoles = new Role("ADMIN");
        when(roleService.addRoleToUser(userRolesToModify)).thenReturn((modifiedUserRoles));
        ReadRoleDto responseRoleDto = new ReadRoleDto(1L, "ADMIN");
        when((dto.mapToReadRoleDto(modifiedUserRoles))).thenReturn(responseRoleDto);
        //When
        ReadRoleDto result = roleController.addRoleToUser(requestInputDataRoleDtoToUser).getBody();
        //Then
        assertEquals("ADMIN", result.getRoleName());
        verify(roleService, times(1)).addRoleToUser(userRolesToModify);
    }

    @Test
    void shouldRemoveRoleFromUser() throws RoleNotFoundException, UserNotFoundException, RoleIsAlreadyRemoveException {
        //Given
        RoleController roleController = new RoleController(roleService, dto);
        InputDataRoleDto requestInputDataRoleDtoToUser = new InputDataRoleDto("ADMIN", 1L);
        Role userRolesToModify = new Role("ADMIN");
        when(dto.mapToRole(requestInputDataRoleDtoToUser)).thenReturn(userRolesToModify);
        Role modifiedUserRoles = new Role("USER");
        when(roleService.removeUserRoles(userRolesToModify)).thenReturn(modifiedUserRoles);
        ReadRoleDto responseRoleDto = new ReadRoleDto(1L, "ADMIN");
        when(dto.mapToReadRoleDto(modifiedUserRoles)).thenReturn(responseRoleDto);
        //When
        ReadRoleDto result = roleController.removeRoleFromUser(requestInputDataRoleDtoToUser).getBody();
        //Then
        assertEquals("ADMIN", result.getRoleName());
        verify(roleService, times(1)).removeUserRoles(userRolesToModify);
    }
}
