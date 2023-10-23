package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.dto.AddRoleDto;
import com.app.crypto.wallet.domain.dto.ReadRoleDto;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.RoleService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        List<ReadRoleDto> responseReadRoleDto = List.of(new ReadRoleDto("USER"), new ReadRoleDto("ADMIN"));
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
        ReadRoleDto responseReadRoleDto = new ReadRoleDto("USER");
        when(dto.mapToReadRoleDto(roleInDatabase)).thenReturn(responseReadRoleDto);
        //When
        ReadRoleDto result = roleController.getRole(requestRoleId).getBody();
        //Then
        assertEquals("USER", result.getRoleName());
        verify(roleService, times(1)).getRoleById(requestRoleId);
    }

    @Test
    void shouldAddRoleToUser() {
        //Given
        RoleController roleController = new RoleController(roleService, dto);
        AddRoleDto requestAddRoleDto = new AddRoleDto("ADMIN");
        Role addNewRoleToUser = new Role("ADMIN");
        when(dto.mapToRole(requestAddRoleDto)).thenReturn(addNewRoleToUser);
        Role userRoleInDatabase = new Role("USER");
        when(roleService.addRoleToUser(addNewRoleToUser)).thenReturn(userRoleInDatabase);
        AddRoleDto responseRoleDto = new AddRoleDto("ADMIN");
        when((dto.mapToAddRoleDto(userRoleInDatabase))).thenReturn(responseRoleDto);
        //When
        AddRoleDto result = roleController.addRoleToUser(requestAddRoleDto).getBody();
        //Then
        assertEquals("ADMIN", result.getRoleName());
        verify(roleService, times(1)).addRoleToUser(addNewRoleToUser);
    }

    @Test
    void shouldRemoveRoleFromUser() {
        //Given
        RoleController roleController = new RoleController(roleService, dto);
        //When
        //Then
    }
}
